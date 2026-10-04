/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz.lib;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator1
 */
public class ParseData {

    private JSONObject obj;

    public ParseData(JSONObject json) {
        this.obj = json;
    }

    public Object getObject(String key) {
        return obj.get(key);
    }

    public byte getByte(String key) {
        try {
            Object val = obj.get(key);
            if (val == null) {
                return 0;
            }
            return Byte.parseByte(val.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public short getShort(String key) {
        try {
            Object val = obj.get(key);
            if (val == null) {
                return 0;
            }
            return Short.parseShort(val.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public int getInt(String key) {
        try {
            Object val = obj.get(key);
            if (val == null) {
                return 0;
            }
            return Integer.parseInt(val.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public long getLong(String key) {
        try {
            Object val = obj.get(key);
            if (val == null) {
                return 0L;
            }
            return Long.parseLong(val.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    public String getString(String key) {
        try {
            return obj.get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean getBoolean(String key) {
//        try{
//            return Boolean.parseBoolean(obj.get(key).toString());
//
//        }catch (NullPointerException e){
//            return false;
//        }
        if (obj.get(key) == null) {
            return false;
        } else if (obj.get(key).toString() != null) {
            return Boolean.parseBoolean(obj.get(key).toString());
        } else {
            return false;
        }

    }

    public Date getDate(String key, String dateFormat) throws ParseException {
        try {
            String content = obj.get(key).toString();
            Date date = new SimpleDateFormat(dateFormat).parse(content);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONArray getJSONArray(String key) {
        try {
            return (JSONArray) JSONValue.parseWithException(obj.get(key).toString());
        } catch (Exception e) {
            return null;
        }
    }

    public ParseData getParseData(String key) {
        try {
            Object str = obj.get(key);
            if (str == null) {
                return null;
            }
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(str.toString());
            return new ParseData(jsonObject);
        } catch (Exception e) {
            return null;
        }
    }

    public ParseData[] getArrayParseData(String key) {
        try {
            JSONArray jsonArray = getJSONArray(key);
            int len = jsonArray.size();
            ParseData[] arr = new ParseData[len];
            for (int i = 0; i < len; i++) {
                arr[i] = new ParseData((JSONObject) jsonArray.get(i));
            }
            return arr;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean containsKey(String key) {
        return this.obj.containsKey(key);
    }

    public boolean containsValue(String key) {
        return this.obj.containsValue(key);
    }

    public boolean isEmpty() {
        return obj.isEmpty();
    }

    public double getDouble(String key) {
        try {
            Object val = obj.get(key);
            if (val == null) {
                return 0.0;
            }
            return Double.parseDouble(val.toString());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
