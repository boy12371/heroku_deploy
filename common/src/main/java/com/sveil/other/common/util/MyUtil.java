package com.sveil.other.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyUtil.class);

	private static MyUtil huu = new MyUtil();

	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd-HH:mm:ss");

	private final static SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyyMMdd");

	public static MyUtil getInstance() {
		return huu;
	}

	private MyUtil() {
	}

	public static String nvl(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	@SuppressWarnings("resource")
	public void copyFile(File from, File to) throws Exception {
		FileChannel inC = new FileInputStream(from).getChannel();
		FileChannel outC = new FileOutputStream(to).getChannel();
		ByteBuffer b = null;
		int length = 1024 * 1024 * 2;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 2097152;
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}

	/**
	 * 功能: 删除指定目录及其中的所有内容。
	 * 
	 * @param dir
	 *            要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 */
	public boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}
		File[] entries = dir.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}
		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	public String getRandom(int length) {
		String seed = "abcd2wefg3da45678hijklmnopqrstuvwxyzABcdhnCDsxqaEFGHIJKLMNOPQRSTUVWXYZ"
				+ System.currentTimeMillis();
		String orderNo = "";
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randnumber = random.nextInt(seed.length());
			String strRand = seed.substring(randnumber, randnumber + 1);
			orderNo += strRand;
		}
		return orderNo;
	}

	/**
	 * 补零
	 * 
	 * @param n
	 * @param length
	 * @return
	 */
	public String addZore(Integer n, int length) {
		StringBuffer t = new StringBuffer("");
		for (int i = 0; i < length - n.toString().length(); i++) {
			t.append("0");
		}
		return t.toString() + n.toString();
	}

	/**
	 * 判断字符串是否为
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNullEmpty(String str) {
		return org.apache.commons.lang3.StringUtils.isEmpty(str);
	}

	/**
	 * 分割字符
	 * 
	 * @param str
	 * @return
	 */
	private final static char splitChar = 6;

	public String[] splitStr(String str) {
		return str.split("" + splitChar);
	}

	public Date getDate(String date) {
		String[] dates = StringUtils.split(date, "-");
		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
				.getInstance();
		gc.set(new Integer(dates[0]).intValue(),
				new Integer(dates[1]).intValue() - 1,
				new Integer(dates[2]).intValue());
		return gc.getTime();
	}

	public Date getAllDatePart(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}

	public String getDateyyyMMdd(String date) {
		return formateDate(getDate(date), "yyyy-MM-dd");
	}

	public Date TimestampToDate(java.sql.Timestamp tt) {
		GregorianCalendar gc = (GregorianCalendar) GregorianCalendar
				.getInstance();
		gc.setTimeInMillis(tt.getTime());
		return gc.getTime();
	}

	public String formateDate(Date date, String pattern) {
		return org.apache.commons.lang3.time.DateFormatUtils.format(date,
				pattern);
	}

	public boolean isAllLetter(String inString) {
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m = p.matcher(inString);
		return m.matches();
	}

	public boolean isNumber(String number) {
		return org.apache.commons.lang3.math.NumberUtils.isNumber(number);
	}

	public boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("[+]?[0-9]{11,15}");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public boolean isPhone(String phone) {
		Pattern p = Pattern.compile("[0][1-9]{2,3}[-]?[0-9]{6,9}");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	public boolean isZip(String zip) {
		Pattern p = Pattern.compile("[0-9]{5,8}");
		Matcher m = p.matcher(zip);
		return m.matches();
	}

	public boolean isEmail(String email) {
		Pattern p = Pattern
				.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public boolean isMemberName(String name) {
		Pattern p = Pattern.compile("[a-zA-Z0-9_]{3,15}");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	public boolean isPs(String name) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{6,20}");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * @return 得到工程当前目录
	 */
	public String getRealPath() {
		String path = this.getClass().getResource("").getPath();
		path = path.substring(1, path.indexOf("WEB-INF/"));
		return path;
	}

	/**
	 * 把文本编码为Html代码
	 * 
	 * @param target
	 * @return 编码后的字符
	 */
	public String htmEncode(String target) {
		StringBuffer stringbuffer = new StringBuffer();
		int j = target.length();
		for (int i = 0; i < j; i++) {
			char c = target.charAt(i);
			switch (c) {
			case 60:
				stringbuffer.append("&lt;");
				break;
			case 62:
				stringbuffer.append("&gt;");
				break;
			case 38:
				stringbuffer.append("&amp;");
				break;
			case 34:
				stringbuffer.append("&quot;");
				break;
			case 169:
				stringbuffer.append("&copy;");
				break;
			case 174:
				stringbuffer.append("&reg;");
				break;
			case 165:
				stringbuffer.append("&yen;");
				break;
			case 8364:
				stringbuffer.append("&euro;");
				break;
			case 8482:
				stringbuffer.append("&#153;");
				break;
			case 13:
				if (i < j - 1 && target.charAt(i + 1) == 10) {
					stringbuffer.append("<br>");
					i++;
				}
				break;
			case 32:
				if (i < j - 1 && target.charAt(i + 1) == ' ') {
					stringbuffer.append(" &nbsp;");
					i++;
					break;
				}
			default:
				stringbuffer.append(c);
				break;
			}
		}
		return new String(stringbuffer.toString());
	}

	/**
	 * 把时间转为标准形式
	 * 
	 * @param dt
	 * @return 转换后的形式
	 */
	public String toString(Date dt, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(dt);
		return str;
	}

	public Date toDate(String dt) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = df.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public Date getCurrenTime() {
		return java.util.Calendar.getInstance().getTime();
	}

	/**
	 * 
	 * 功能：将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
	 * 
	 * @author richard
	 * @param strIp
	 * @return
	 */
	public long ipToLong(String strIp) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 
	 * 功能：将十进制整数形式转换成127.0.0.1形式的ip地址
	 * 
	 * @author richard
	 * @param longIp
	 * @return
	 */
	public String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((longIp >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((longIp & 0x000000FF)));
		return sb.toString();
	}

	public static String buildCommandOrNo(String param, String commandOrNo,
			String channelNo) {
		if ("".equals(param)) {
			return commandOrNo;
		} else {
			return replaceParam(commandOrNo, param, channelNo);
		}
	}

	public static String replaceParam(String content, String param, String value) {
		return content.replace("${" + param + "}", value);
	}

	public static String extractParam(String param) {

		if (param.indexOf("${") != -1) {

			return param.substring(param.indexOf("${") + 2, param.indexOf("}"));
		} else {

			return "";
		}
	}

	public static String idDomain(String id) {
		return "id(" + id + ")";
	}

	/**
	 * 格式化日期 yyyy-MM-dd-HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return sdf.format(date);
	}

	public static String format(Calendar calendar) {
		return sdf1.format(calendar.getTime());
	}

	/**
	 * 根据type类型，获取day, month, week, year的起止时间，type=day,week,mon,year
	 * 
	 * @param type
	 * @return
	 */
	public String[] getStartDateAndEndDate(String type) {
		String[] retStringArray = new String[2];
		if (StringUtils.isBlank(type))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if (type.equalsIgnoreCase("day")) {
			retStringArray[0] = sdf.format(new Date());
			retStringArray[1] = sdf.format(new Date());
		} else if (type.equalsIgnoreCase("week")) {
			int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
			cal.add(Calendar.DATE, -day_of_week);
			retStringArray[0] = sdf.format(cal.getTime());
			cal.add(Calendar.DATE, 6);
			retStringArray[1] = sdf.format(cal.getTime());
		} else if (type.equalsIgnoreCase("mon")) {
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH,
					cal.getMinimum(Calendar.DAY_OF_MONTH));
			retStringArray[0] = sdf.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH,
					cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			retStringArray[1] = sdf.format(cal.getTime());
		} else if (type.equalsIgnoreCase("year")) {
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_YEAR, cal.getMinimum(Calendar.DAY_OF_YEAR));
			retStringArray[0] = sdf.format(cal.getTime());
			cal.set(Calendar.DAY_OF_YEAR,
					cal.getActualMaximum(Calendar.DAY_OF_YEAR));
			retStringArray[1] = sdf.format(cal.getTime());
		} else {
			retStringArray = null;
		}
		return retStringArray;
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @author richard
	 * @return
	 */
	public Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @author richard
	 * @return
	 */
	public Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到几小时前的时间
	 * 
	 * @param d
	 * @param hour
	 * @author richard
	 * @return
	 */
	public Date getDateHourBefore(Date d, int hour) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) - hour);
		return now.getTime();
	}

	/**
	 * 得到几小时后的时间
	 * 
	 * @param d
	 * @param hour
	 * @author richard
	 * @return
	 */
	public Date getDateHourAfter(Date d, int hour) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
		return now.getTime();
	}

	/**
	 * 得到几秒前的时间
	 * 
	 * @param d
	 * @param hour
	 * @author richard
	 * @return
	 */
	public Date getDateSecondBefore(Date d, int second) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.SECOND, now.get(Calendar.SECOND) - second);
		return now.getTime();
	}

	/**
	 * 得到几秒后的时间
	 * 
	 * @param d
	 * @param hour
	 * @author richard
	 * @return
	 */
	public Date getDateSecondAfter(Date d, int second) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.SECOND, now.get(Calendar.SECOND) + second);
		return now.getTime();
	}

	/**
	 * 获取两个时间的相差分钟数组
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<String> getMins(String beginTime, String endTime)
			throws Exception {
		Date inputTimeBegin = this.getAllDatePart("2010-01-01 " + beginTime
				+ ":00");
		Date inputTimeEnd = this
				.getAllDatePart("2010-01-01 " + endTime + ":00");
		List<String> inputList = new ArrayList<String>();
		while (inputTimeBegin.compareTo(inputTimeEnd) <= 0) {
			inputList.add(this.formateDate(inputTimeBegin, "HH:mm"));
			inputTimeBegin = org.apache.commons.lang3.time.DateUtils
					.addMinutes(inputTimeBegin, 1);
		}
		return inputList;
	}

	/**
	 * 获取特定范围内的随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public int getRandom(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	public double round(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	public float round(float value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		float d = bd.floatValue();
		bd = null;
		return d;
	}

	public String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;
		Pattern p_html1;
		Matcher m_html1;
		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			LOGGER.error("ERROR::", e);
		}
		return textStr;// 返回文本字符串
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List removeDuplicate(List list) {
		List retList = new ArrayList();
		HashSet h = new HashSet(list);
		retList.addAll(h);
		return retList;
	}

	public int daysBetween(Date early, Date late) {
		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;
		return days;
	}

	/**
	 * 将一个2byte的数组转换成16的有符号short类型 (大端)
	 * 
	 * ＠param buf bytes buffer ＠param byte[]中开端转换的地位 ＠return convert result
	 */
	public short signed2ByteToShort(byte[] buf, int pos) {
		int firstByte = 0;
		int secondByte = 0;
		int index = pos;
		firstByte = (0x000000FF & ((short) buf[index]));
		secondByte = (0x000000FF & ((short) buf[index + 1]));
		return (short) (firstByte << 8 | secondByte);
	}

	/**
	 * 将16位的short转换成byte数组 (大端)
	 * 
	 * ＠param s short ＠return byte[] 长度为2
	 * */
	public byte[] shortToByteArray(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

	public String buildUrl(String baseUrl, Map<String, String> params) {
		StringBuffer urlBuf = new StringBuffer(baseUrl);
		Iterator<String> keyIt = params.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			if (urlBuf.toString().contains("?")) {
				urlBuf.append("&");
			} else {
				urlBuf.append("?");
			}
			urlBuf.append(key).append("=").append(params.get(key));
		}
		return urlBuf.toString();
	}

	private boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	public boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}

	public boolean isNegativeInteger(String orginal) {
		return isMatch("^-[1-9]\\d*", orginal);
	}

	public boolean isWholeNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal)
				|| isNegativeInteger(orginal);
	}

	public boolean isPositiveDecimal(String orginal) {
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
	}

	public boolean isNegativeDecimal(String orginal) {
		return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
	}

	public boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}

	public boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}
}
