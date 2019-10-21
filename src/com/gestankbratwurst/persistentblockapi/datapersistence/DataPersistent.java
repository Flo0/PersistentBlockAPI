package com.gestankbratwurst.persistentblockapi.datapersistence;

public interface DataPersistent<T> {
	
	public abstract void saveData(T data);
	public abstract void loadData(T data);
	
	public default PersistentDataFormat getDataFormat() {
		return null;
	}
	
}