package com.derbysoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.derbysoft.entity.cms.GmsAlarmCall;
import com.derbysoft.entity.cms.GmsAlarmCheack;
import com.derbysoft.entity.cms.GmsAlarmReceive;
import com.derbysoft.entity.cms.Member;
import com.derbysoft.entity.sys.SYS_User;

public class StringToJsonUtils {

/*	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
<<<<<<< .mine
		long l = System.currentTimeMillis();
		int a = 10000;
		int i = 0;
		while(i < a){
			
			i++;
		}
		long e = System.currentTimeMillis();
		System.out.println(e-l);
=======
	Map<String , Object> m = new HashMap<String , Object>();
	m.put("1", 1);
	m.put("2", 2);
	System.out.println(m.toString());
	m.remove("1");
	System.out.println(m.toString());
	ArrayList l= null;
	l=new ArrayList();
	System.out.println(l.size());
>>>>>>> .r733
	}*/
public static void main(String[] args) {
	
	List<SYS_User> list =  new ArrayList<SYS_User>();
	System.out.println(list.size());
}
	@Test
	public void test1() {
		Map<String , Object> m = new HashMap<String , Object>();
		GmsAlarmCall a = new GmsAlarmCall();
		// System.out.println(a.toString());
		SYS_User s = new SYS_User();
		s.setAlias("121");
		// m.put("alarmCall", a);
		m.put("poliseFirst", s);
		JSONObject s1 = JSONObject.fromObject(a);
		// String s =
		// "{\"alarmCall\": {\"adressID\": \"122455\",\"asd\":\"sss\"},\"asd\":\"sss\"}";
		// JSONObject m1 = JSONObject.fromObject(s);
		// String object = (String) m1.get("asd");
		// System.out.println(object);
		GmsAlarmCall bean = (GmsAlarmCall) JSONObject.toBean(
				(JSONObject) s1.get("alarmCall"), GmsAlarmCall.class);
		// System.out.println(bean.getAdressID());
		SYS_User object = (SYS_User) JSONObject.toBean((JSONObject) JSONObject
				.fromObject(m).get("poliseFirst"), SYS_User.class);
		// System.out.println(s.get("addressID").equals(""));
		System.out.println("".equals(null));
	}

	@Test
	public void test2() {
		Map<String,Object> m = new HashMap<String,Object>();
		GmsAlarmCall a = new GmsAlarmCall();
		m.put("alarmCall", a);
		Map fromObject = JSONObject.fromObject(m);
		// GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCall");

//		GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCalls");
	
		System.out.println(fromObject);
		// System.out.println(fromObject.get("alarmCall"));
	}

	@Test
	public void test3() {
		Map m = new HashMap();
		Member a = new Member();
		m.put("hasmember", a);
		Map fromObject = JSONObject.fromObject(m);
		// GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCall");

		System.out.println(fromObject);
		// System.out.println(fromObject.get("alarmCall"));
	}

	@Test
	public void test5() {
		Map m = new HashMap();
		SYS_User a = new SYS_User();
		m.put("poliseInfo", a);
		m.put("123", "456");
		System.out.println(m.get("123").toString());
		Map fromObject = JSONObject.fromObject(m);
		// GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCall");

		System.out.println(fromObject);
		// System.out.println(fromObject.get("alarmCall"));
	}

	@Test
	public void test4() {
		Map m = new HashMap();
		GmsAlarmReceive a = new GmsAlarmReceive();
		m.put("alarmReceive", a);
		Map fromObject = JSONObject.fromObject(m);
		// GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCall");

		System.out.println(fromObject);
		// System.out.println(fromObject.get("alarmCall"));
	}

	@Test
	public void test6() {
		Map m = new HashMap();
		GmsAlarmCheack a = new GmsAlarmCheack();
		m.put("alarmCheack", a);
		Map fromObject = JSONObject.fromObject(m);
		// GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCall");

		System.out.println(fromObject);
		// System.out.println(fromObject.get("alarmCall"));
	}

	@Test
	public void test7() {
		Map m = new HashMap();
		m.put("1", 1);
		m.put("2", 2);
		m.put("3", 3);
		m.remove("4");
		// GmsAlarmCall object = (GmsAlarmCall) fromObject.get("alarmCall");

		System.out.println(m);
		// System.out.println(fromObject.get("alarmCall"));
	}

	// public void test3(){
	// String str =
	// "{\"lendperson\":\"李四\",\"lendcompany\":\"有限公司\",\"checkperson\":\"李四\",\"lenddate\":\"2010-07-19T00:00:00\",\"lendcounts\":4,\" passports\":[{\"passportid\":\"d\",\"name\":\"李豫川\",\"passporttype\":\"K\"},{\"passportid\":\"K9051\",\"name\":\"李平\",\"passporttype\":\"K\"},{\"passportid\":\"K90517\",\"name\":\"袁寒梅\",\"passporttype\":\"K\"},{\"passportid\":\"K905199\",\"name\":\"贺明\",\"passporttype\":\"K\"}]}";
	// JSONObject jsonobject = JSONObject.fromObject(str);
	// PassportLendsEntity passportlends = null;
	// try {
	// //获取一个json数组
	// JSONArray array = jsonobject.getJSONArray("passports");
	// //将json数组 转换成 List<PassPortForLendsEntity>泛型
	// List<PassPortForLendsEntity> list = new
	// ArrayList<PassPortForLendsEntity>();
	// for (int i = 0; i < array.size(); i++) {
	// JSONObject object = (JSONObject)array.get(i);
	// PassPortForLendsEntity passport =
	// (PassPortForLendsEntity)JSONObject.toBean(object,
	// PassPortForLendsEntity.class);
	// if(passport != null){
	// list.add(passport);
	// }
	// }
	// //转换PassportLendsEntity 实体类
	// passportlends = (PassportLendsEntity)JSONObject.toBean(jsonobject,
	// PassportLendsEntity.class);
	// }
	public static void bubbleSort(int a[]) {
		int len = a.length;
		for (int i = 0; i < len - 1; i++) {
			for (int j = 0; j < len - 1 - i; j++) {
				if (a[j] > a[j + 1]) {
					int temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
	}

	public static void selectSort(int a[]) {
		int temp = 0;
		int len = a.length;
		for (int i = 0; i < len - 1; i++) {
			int min = a[i];
			int index = i;
			for (int j = i + 1; j < len; j++) {
				if (min > a[j]) {
					min = a[j];
					index = j;
				}
			}
			temp = a[i];
			a[i] = a[index];
			a[index] = temp;
		}
	}

	public static void insertSort(int a[]) {
		int len = a.length;
		for (int i = 1; i < len; i++) {
			int temp = a[i];// 待插入的值
			int index = i;// 待插入的位置
			while (index > 0 && a[index - 1] > temp) {
				a[index] = a[index - 1];// 待插入的位置重新赋更大的值
				index--;// 位置往前移
			}
			a[index] = temp;
		}
	}

	public static int partition(int a[], int low, int height) {
		int key = a[low];
		while (low < height) {
			while (low < height && a[height] >= key)
				height--;
			a[low] = a[height];

			while (low < height && a[low] <= key)
				low++;
			a[height] = a[low];
		}
		a[low] = key;
		return low;
	}

	@Test
	public static void quickSort(int a[], int low, int height) {
		if (low < height) {
			int result = partition(a, low, height);
			quickSort(a, low, result - 1);
			quickSort(a, result + 1, height);
		}
	}
}
