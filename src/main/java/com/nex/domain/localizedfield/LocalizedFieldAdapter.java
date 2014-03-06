package com.nex.domain.localizedfield;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.nex.domain.Language;

public class LocalizedFieldAdapter implements LocalizedValue {

	private List<LocalizedField> localizedTexts;

	private String localizedFieldName;

	private Class<?> recordClass;

	public LocalizedFieldAdapter(Class<?> recordClass,
			List<LocalizedField> localizedTexts, String localizedFieldName) {
		super();
		this.recordClass = recordClass;
		if (localizedTexts != null) {
			this.localizedTexts = localizedTexts;
		} else {
			this.localizedTexts = new ArrayList<LocalizedField>(0);
		}
		this.localizedFieldName = localizedFieldName;
	}

	public Class<?> getRecordClass() {
		return recordClass;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.tsystems.bi.module.common.localizedfield.Localized#size()
	 */
	@Override
	public int size() {
		return this.localizedTexts.size();
	}

	private LocalizedField getMessage(Object key) {
		for (LocalizedField lm : localizedTexts) {
			if (key.equals(lm.getLanguageCode())) {
				return lm;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.tsystems.bi.module.common.localizedfield.Localized#get(java.lang.Object
	 * )
	 */
	@Override
	public String get(Language key) {
		return getByCode(key.getId());
	}

	@Override
	public String get(String langKey) {
		return getByCode(langKey);
	}

	@Override
	public String get(Locale locale) {
		return getByCode(locale.getLanguage());
	}

	protected String getByCode(String key) {
		LocalizedField lm = getMessage(key);
		return lm != null ? readMessageText(lm) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.tsystems.bi.module.common.localizedfield.Localized#getNullSafe(java
	 * .lang.Object)
	 */
	@Override
	public String getNullSafe(Language selectedLanguage) {
		return _getNullSafe(selectedLanguage.getId());
	}

	@Override
	public String getNullSafe(Locale locale) {
		return _getNullSafe(locale.getLanguage());
	}

	private String _getNullSafe(String langCode) {
		String defaultMessage = getByCode(langCode);
		if (defaultMessage == null) {
			for (LocalizedField localizedField : localizedTexts) {
				String langId = localizedField.getLanguageCode();
				defaultMessage = getByCode(langId);
				if (defaultMessage != null) {
					break;
				}
			}
		}

		return defaultMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.tsystems.bi.module.common.localizedfield.Localized#put(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public void put(Language key, String value) {
		put(key.getId(), value);
	}

	protected String put(String key, String value) {
		try {
			LocalizedField lm = getMessage(key);
			if (value != null && value.length() != 0) {
				if (lm == null) { // vytvori novy zaznam pokud neexistuje
					lm = (LocalizedField) recordClass.newInstance();
					lm.setLanguageCode(key);
					this.localizedTexts.add(lm);
				}
				String prev = readMessageText(lm);
				writeMessageText(lm, value);
				return prev;
			} else {
				// prazdne value se povazuje za neexistujici hodnotu
				return remove(key);// pokud jiz existuje tak se smaze
			}
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}
	@Override
	public Map<String, String> getAsMap() {
		return new LocalizedFieldAdapterMap(this);
	}

	protected String remove(String key) {
		for (Iterator<LocalizedField> it = localizedTexts.iterator(); it
				.hasNext();) {
			LocalizedField lm = it.next();
			if (key.equals(lm.getLanguageCode())) {
				it.remove();
				return "";
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.tsystems.bi.module.common.localizedfield.Localized#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("messages:");
		for (LocalizedField msg : localizedTexts) {
			sb.append(msg.getLanguageCode()).append("=")
					.append(readMessageText(msg)).append(", ");
			sb.append(";");
		}
		return sb.toString();
	}

	private String readMessageText(LocalizedField msg) {
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(
				this.recordClass, localizedFieldName);
		try {
			Object value = pd.getReadMethod().invoke(msg);
			return (String) value;
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}

	private void writeMessageText(LocalizedField msg, String message) {
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(
				this.recordClass, localizedFieldName);
		try {
			pd.getWriteMethod().invoke(msg, message);
			// Object localizable = msg.getLocalizable();
			// if (localizable instanceof ModificationInfo) {
			// ((ModificationInfo)localizable).markAsChanged();
			// }
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}
	protected List<LocalizedField> getLocalizedTexts() {
		return localizedTexts;
	}
}
