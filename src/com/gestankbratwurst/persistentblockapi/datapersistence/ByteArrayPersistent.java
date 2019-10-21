package com.gestankbratwurst.persistentblockapi.datapersistence;

import com.gestankbratwurst.persistentblockapi.util.ByteStash;

public interface ByteArrayPersistent extends DataPersistent<ByteStash> {
	
	@Override
	public default PersistentDataFormat getDataFormat() {
		return PersistentDataFormat.BYTE_ARRAY;
	}
	
}