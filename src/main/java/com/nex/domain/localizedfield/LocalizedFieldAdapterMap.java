package com.nex.domain.localizedfield;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nex.annotation.ShortString;

public class LocalizedFieldAdapterMap implements Map<String, String> {
	private Logger log = LoggerFactory.getLogger(LocalizedFieldAdapterMap.class);
	private LocalizedFieldAdapter adapter;
	private ShortString shortString = null;
	public LocalizedFieldAdapterMap(LocalizedFieldAdapter adapter) {
		super();
		this.adapter = adapter;
		
		try {
			Class<?> cls = adapter.getRecordClass();
			Field f = cls.getDeclaredField("value");
			this.shortString = f.getAnnotation(ShortString.class);
		} catch (Exception e) {
			log.error("", e);
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.tsystems.bi.module.common.localizedfield.Localized#size()
	 */
	@Override
	public int size() {
		return adapter.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.tsystems.bi.module.common.localizedfield.Localized#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return adapter.getLocalizedTexts().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.tsystems.bi.module.common.localizedfield.Localized#containsKey
	 * (java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException(
				"This operation is not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.tsystems.bi.module.common.localizedfield.Localized#get(java.lang
	 * .Object)
	 */
	@Override
	public String get(Object key) {
		if (!(key instanceof String)) {
			return null;
		}
		return substr(adapter.getByCode((String) key));
	}

	private String substr(String value) {
		if(value == null) return value;
		if(this.shortString != null) {
			
			int min = this.shortString.min();
			int max = this.shortString.max();
			int length = value.length();
			if(max > length || max < 0) {
				max = length;
			}
			if(min > length || min < 0) {
				min = 0;
			}
			value = value.substring(min, max);
		}
		return value;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.tsystems.bi.module.common.localizedfield.Localized#put(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public String put(String key, String value) {
		return adapter.put(key, value);
	}

	public String remove(Object key) {
		return adapter.remove((String) key);
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		throw new UnsupportedOperationException(
				"This operation is not implemented yet!");
	}

	public void clear() {
		throw new UnsupportedOperationException(
				"This operation is not implemented yet!");
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException(
				"This operation is not implemented yet!");
	}

	public Collection<String> values() {
		throw new UnsupportedOperationException(
				"This operation is not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.tsystems.bi.module.common.localizedfield.Localized#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		Set<java.util.Map.Entry<String, String>> result = new HashSet<Map.Entry<String, String>>(
				adapter.getLocalizedTexts().size());
		for (LocalizedField lm : adapter.getLocalizedTexts()) {
			result.add(new LocalizedFieldEntry(lm));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.tsystems.bi.module.common.localizedfield.Localized#toString()
	 */
	@Override
	public String toString() {
		return adapter.toString();
	}

	/**
	 * Map.Entry
	 * 
	 * @author MacikJ
	 */
	private class LocalizedFieldEntry implements Map.Entry<String, String> {

		private LocalizedField lm;

		public LocalizedFieldEntry(LocalizedField lm) {
			super();
			this.lm = lm;
		}

		public String getKey() {
			return lm.getLanguageCode();// TODO
		}

		public String getValue() {
			return get(lm.getLanguageCode());
		}

		public String setValue(String value) {
			return put(lm.getLanguageCode(), value);
		}

	}

}
