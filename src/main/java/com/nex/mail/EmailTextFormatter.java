package com.nex.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;

import com.nex.mail.evaluators.Evaluator;
import com.nex.mail.parser.EmailValuesParser;
import com.nex.mail.parser.ExcludedEmailValuesParser;
import com.nex.mail.parser.NoValuesParser;
import com.nex.mail.value.DefaultValueResolver;

/**
 * Trida nacita text emailu z MaiTexts.properties a automaticky umi doplnovat
 * parametry, ktere vytahne z entity (nebo jakehokoliv jineho objektu, ale POUZE
 * pomoci GETTERU), popripade lze nadefinovat vlastni Evaluator, kterym lze za
 * parameter doplnit vlastni hodnotu. Take umi vyuzivat springConversionService.
 * 
 * Pouziti:
 * 
 * mail.text.test = Test email Jmeno: {0} Ulozeno: {1} Vlastni hodnota: {2}
 * mail.text.test.values = employee.fullName, date, customValue,
 * listName[index].fieldName
 * 
 * employee.fullname: je cesta ke konecne hodnote v entite. date: je datum ktery
 * nam Spring zkonvertuje do stringu. customValue: je vlastni implementace
 * {@link Evaluator} a nastavuje se v applicationContext.xml
 * 
 * Do evaluatoru se posila samotna entita (napriklad kdyz se hodnota lisi na
 * zaklade ruznyh hodnot), ktery Evaluator slouzi spise pro jine hodnoty, ktere
 * se ziskavaji napriklad z requestu ({@link RequestHolder}), nebo nejake
 * staticke tridy a podobne.
 * 
 * @author TremlL
 * 
 */
public class EmailTextFormatter {

	private static final Logger log = LoggerFactory
			.getLogger(EmailTextFormatter.class);

	/**
	 * Evaluator is object which returns special value when value is not
	 * contained in entity.
	 */
	private Map<String, Evaluator> evaluators = new HashMap<String, Evaluator>();

	private List<EmailValuesParser> parsers = new ArrayList<EmailValuesParser>();

	private DefaultValueResolver defaultValueResolver;
	
	/**
	 * Converts data type to string when formatter exists.
	 */
	protected ConversionService springConversionService;

	private MessageSource mailSource;
	private MessageSource templates;
	
	public EmailTextFormatter() {
		initDefaults();
	}
	
	public void setMailSource(MessageSource mailSource) {
		this.mailSource = mailSource;
	}
	
	public void setTemplates(MessageSource templates) {
		this.templates = templates;
	}

	public void setEvaluators(Map<String, Evaluator> evaluators) {
		this.evaluators = evaluators;
	}

	public void setParsers(List<EmailValuesParser> parsers) {
		Collections.sort(parsers);
		this.parsers = parsers;
	}

	public void setDefaultValueResolver(
			DefaultValueResolver defaultValueResolver) {
		this.defaultValueResolver = defaultValueResolver;
	}
	
	public void setSpringConversionService(
			ConversionService springConversionService) {
		this.springConversionService = springConversionService;
	}

	public String getTemplate(String key, Object data, Locale locale, Object... evaluatorData) {
		return getText(key, templates, data, locale, evaluatorData);
	}
	
	public String getSubject(String key, Object data, Locale locale, Object... evaluatorData) {
		String subjectKey = key + ".subject";
		return getText(subjectKey, data, locale, evaluatorData);
	}
	
	public String getText(String key, Object data, Locale locale, Object... evaluatorData) {
		return getText(key, mailSource, data, locale, evaluatorData);
	}
	
	private void initDefaults() {
		this.parsers.add(new ExcludedEmailValuesParser());
		this.parsers.add(new NoValuesParser());
	}
	
	public String getText(String key, MessageSource mailSource, Object data, Locale locale, Object... evaluatorData) {
		
		EmailValuesParser parser = findParser(key, mailSource, locale);
		//pokud se nenajde zadny handler na ziskavani metody hodnot znamena to, ze email nema zadna argumenty
		if(parser == null) return this.mailSource.getMessage(key, null, locale);
		String[] valuesKeys = parser.getValues(key, mailSource, locale);
		Object[] arguments = new Object[valuesKeys.length];
		key = parser.getFormatableKey(key, valuesKeys);
		for (int i = 0; i < valuesKeys.length; i++) {
			String vKey = valuesKeys[i].trim();
			ValueFactory vf = new ValueFactory(vKey, defaultValueResolver);
			Evaluator ev = findCandidateEvaluator(vf.getValueGenerator().getKey());
			try {
				Object value = null;
				if (ev != null) {
					value = ev.getValue(vf.getValueGenerator().getKey(), vf.getValueGenerator().getDefaultValue(), evaluatorData);
				} else {
					value = vf.getValue(data, evaluatorData);
				}
				if (springConversionService != null) {
					if (springConversionService.canConvert(value.getClass(),
							String.class)) {
						value = springConversionService.convert(value,
								String.class);
					}
				}
				if (value == null) {
					log.warn("Email key: " + key + " > value for vkey " + vKey
							+ " is null.");
				}
				arguments[i] = value;
			} catch (Exception e) {
				throw new RuntimeException("Path " + vKey
						+ " not found in object " + data, e);
			}
		}
		return this.mailSource.getMessage(key, arguments, locale);
	}

	public Evaluator findCandidateEvaluator(String key) {
		Evaluator evaluator = this.evaluators.get(key);
		if(evaluator == null) {
			for(Map.Entry<String, Evaluator> eval: this.evaluators.entrySet()) {
				if(eval.getValue().isMutable(key)) {
					return eval.getValue();
				}
			}
		}
		return evaluator;
	}
	
	private EmailValuesParser findParser(String key, MessageSource messageSource, Locale locale) {
		for(EmailValuesParser parser: parsers) {
			if(parser.canParse(key, messageSource, locale)) {
				return parser;
			}
		}
		throw new RuntimeException("No " + EmailValuesParser.class.getName() + " implementation found for mail_key: " + key + "");
	}

}
