package tool;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * 绫荤浉鍏崇殑宸ュ叿绫�
 * 
 * @author <a href="mailto:ohergal@gmail.com">ohergal</a>
 * 
 */
public class ClassUtil {
    
    public static void main(String[] args) throws Exception{
        List<Class> classes = ClassUtil.getAllClassByInterface(Class.forName("com.threeti.dao.base.IGenericDao"));
        for (Class clas :classes) {
            System.out.println(clas.getName());
        }
    }
    
    /**
     * 鍙栧緱鏌愪釜鎺ュ彛涓嬫墍鏈夊疄鐜拌繖涓帴鍙ｇ殑绫�
     * */
    public static List<Class> getAllClassByInterface(Class c) {
            List<Class>  returnClassList = null;
            
            if(c.isInterface()) {
                // 鑾峰彇褰撳墠鐨勫寘鍚�
                String packageName = c.getPackage().getName();
                // 鑾峰彇褰撳墠鍖呬笅浠ュ強瀛愬寘涓嬫墍浠ョ殑绫�
                List<Class<?>> allClass = getClasses(packageName);
                if(allClass != null) {
                    returnClassList = new ArrayList<Class>();
                    for(Class classes : allClass) {
                        // 鍒ゆ柇鏄惁鏄悓涓�涓帴鍙�
                        if(c.isAssignableFrom(classes)) {
                            // 鏈韩涓嶅姞鍏ヨ繘鍘�
                            if(!c.equals(classes)) {
                                returnClassList.add(classes);        
                            }
                        }
                    }
                }
            }
            
            return returnClassList;
        }
 
    
    /*
     * 鍙栧緱鏌愪竴绫绘墍鍦ㄥ寘鐨勬墍鏈夌被鍚� 涓嶅惈杩唬
     */
    public static String[] getPackageAllClassName(String classLocation, String packageName){
        //灏唒ackageName鍒嗚В
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        int packageLength = packagePathSplit.length;
        for(int i = 0; i< packageLength; i++){
            realClassLocation = realClassLocation + File.separator+packagePathSplit[i];
        }
        File packeageDir = new File(realClassLocation);
        if(packeageDir.isDirectory()){
            String[] allClassName = packeageDir.list();
            return allClassName;
        }
        return null;
    }
    
    /**
     * 浠庡寘package涓幏鍙栨墍鏈夌殑Class
     * @param pack
     * @return
     */
    public static List<Class<?>> getClasses(String packageName){
        
        //绗竴涓猚lass绫荤殑闆嗗悎
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //鏄惁寰幆杩唬
        boolean recursive = true;
        //鑾峰彇鍖呯殑鍚嶅瓧 骞惰繘琛屾浛鎹�
        String packageDirName = packageName.replace('.', '/');
        //瀹氫箟涓�涓灇涓剧殑闆嗗悎 骞惰繘琛屽惊鐜潵澶勭悊杩欎釜鐩綍涓嬬殑things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //寰幆杩唬涓嬪幓
            while (dirs.hasMoreElements()){
                //鑾峰彇涓嬩竴涓厓绱�
                URL url = dirs.nextElement();
                //寰楀埌鍗忚鐨勫悕绉�
                String protocol = url.getProtocol();
                //濡傛灉鏄互鏂囦欢鐨勫舰寮忎繚瀛樺湪鏈嶅姟鍣ㄤ笂
                if ("file".equals(protocol)) {
                    //鑾峰彇鍖呯殑鐗╃悊璺緞
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //浠ユ枃浠剁殑鏂瑰紡鎵弿鏁翠釜鍖呬笅鐨勬枃浠� 骞舵坊鍔犲埌闆嗗悎涓�
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)){
                    //濡傛灉鏄痡ar鍖呮枃浠� 
                    //瀹氫箟涓�涓狫arFile
                    JarFile jar;
                    try {
                        //鑾峰彇jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //浠庢jar鍖� 寰楀埌涓�涓灇涓剧被
                        Enumeration<JarEntry> entries = jar.entries();
                        //鍚屾牱鐨勮繘琛屽惊鐜凯浠�
                        while (entries.hasMoreElements()) {
                            //鑾峰彇jar閲岀殑涓�涓疄浣� 鍙互鏄洰褰� 鍜屼竴浜沯ar鍖呴噷鐨勫叾浠栨枃浠� 濡侻ETA-INF绛夋枃浠�
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //濡傛灉鏄互/寮�澶寸殑
                            if (name.charAt(0) == '/') {
                                //鑾峰彇鍚庨潰鐨勫瓧绗︿覆
                                name = name.substring(1);
                            }
                            //濡傛灉鍓嶅崐閮ㄥ垎鍜屽畾涔夌殑鍖呭悕鐩稿悓
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //濡傛灉浠�"/"缁撳熬 鏄竴涓寘
                                if (idx != -1) {
                                    //鑾峰彇鍖呭悕 鎶�"/"鏇挎崲鎴�"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //濡傛灉鍙互杩唬涓嬪幓 骞朵笖鏄竴涓寘
                                if ((idx != -1) || recursive){
                                    //濡傛灉鏄竴涓�.class鏂囦欢 鑰屼笖涓嶆槸鐩綍
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //鍘绘帀鍚庨潰鐨�".class" 鑾峰彇鐪熸鐨勭被鍚�
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //娣诲姞鍒癱lasses
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                      }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        return classes;
    }
    
    /**
     * 浠ユ枃浠剁殑褰㈠紡鏉ヨ幏鍙栧寘涓嬬殑鎵�鏈塁lass
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //鑾峰彇姝ゅ寘鐨勭洰褰� 寤虹珛涓�涓狥ile
        File dir = new File(packagePath);
        //濡傛灉涓嶅瓨鍦ㄦ垨鑰� 涔熶笉鏄洰褰曞氨鐩存帴杩斿洖
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //濡傛灉瀛樺湪 灏辫幏鍙栧寘涓嬬殑鎵�鏈夋枃浠� 鍖呮嫭鐩綍
        File[] dirfiles = dir.listFiles(new FileFilter() {
        //鑷畾涔夎繃婊よ鍒� 濡傛灉鍙互寰幆(鍖呭惈瀛愮洰褰�) 鎴栧垯鏄互.class缁撳熬鐨勬枃浠�(缂栬瘧濂界殑java绫绘枃浠�)
              public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
              }
            });
        //寰幆鎵�鏈夋枃浠�
        for (File file : dirfiles) {
            //濡傛灉鏄洰褰� 鍒欑户缁壂鎻�
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                                      file.getAbsolutePath(),
                                      recursive,
                                      classes);
            }
            else {
                //濡傛灉鏄痡ava绫绘枃浠� 鍘绘帀鍚庨潰鐨�.class 鍙暀涓嬬被鍚�
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //娣诲姞鍒伴泦鍚堜腑鍘�
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}