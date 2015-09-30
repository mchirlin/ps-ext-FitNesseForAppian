package com.appiancorp.ps.automatedtest.servlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.common.ServiceLocator;
import com.appiancorp.suiteapi.process.ProcessDesignService;
import com.appiancorp.suiteapi.type.Datatype;
import com.appiancorp.suiteapi.type.NamedTypedValue;
import com.appiancorp.suiteapi.type.TypeService;
import com.appiancorp.suiteapi.type.TypedValue;
import com.appiancorp.suiteapi.type.exceptions.InvalidTypeException;

public class ExpressionHelper {

	public static Object evaluateExpression(ServiceContext sc, String expression) throws Exception {    
		TypedValue tv;
		ProcessDesignService pds = ServiceLocator.getProcessDesignService(sc);
		tv = pds.evaluateExpression(expression);

		TypeService ts = ServiceLocator.getTypeService(sc);
		Datatype type = ts.getType(tv.getInstanceType());

		return build(ts, type, tv.getValue());
	}

	private static Object build(TypeService ts, Datatype type, Object o)
			throws InvalidTypeException, JSONException {

		if (type.getList() == null) {
			JSONArray a = new JSONArray();
			for (int i = 0; i < ((Object[]) o).length; i++) {
				a.put(i,build(ts, ts.getType(type.getTypeof()), ((Object[]) o)[i]));
			}
			return a;
		} else {
			if (type.isRecordType()) {
				JSONObject jsonKvp = new JSONObject();
				NamedTypedValue[] ntv = type.getInstanceProperties();
				for (int i = 0; i < ntv.length; i++) {
					String attr = ntv[i].getName();
					Datatype subtype = ts.getType(ntv[i].getInstanceType());
					Object val = ((Object[]) o)[i];
					if (val instanceof Object[]) {
						jsonKvp.put(attr, build(ts, subtype, val));
					} else {
						if (val instanceof java.sql.Date) {
							val = ((java.sql.Date)val).getTime();
						} else if (val instanceof java.util.Date) {
							val = ((java.util.Date)val).getTime();
						} else if (val instanceof java.sql.Timestamp) {
							val = ((java.sql.Timestamp)val).getTime();
						}
						jsonKvp.put(attr, val);
					}
				}
				return jsonKvp;
			} else {
				return o;
			}
		}
	}
}
