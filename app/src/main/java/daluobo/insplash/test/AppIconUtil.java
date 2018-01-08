package daluobo.insplash.test;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daluobo on 2018/1/7.
 */

public class AppIconUtil {


    public void gen(){
        List<Double> data = new ArrayList<>();
        data.add(9.0); data.add(2.0);
        data.add(7.17); data.add(4.0);
        data.add(4.0); data.add(4.0);
        data.add(-1.1); data.add(0.0);
        data.add(-2.0);data.add(0.9);
        data.add(-2.0);data.add(2.0);
        //v
        data.add(12.0);
        //c
        data.add(0.0);data.add(1.1);
        data.add(0.9);data.add(2.0);
        data.add(2.0);data.add(2.0);
        //h
        data.add(16.0);
        //c
        data.add(1.1);data.add(0.0);
        data.add(2.0);data.add(-0.9);
        data.add(2.0);data.add(-2.0);
        data.add(22.0);data.add(6.0);
        //c
        data.add(0.0);data.add(-1.1);
        data.add(-0.9);data.add(-2.0);
        data.add(-2.0);data.add(-2.0);
        data.add(-3.17);
        data.add(15.0);data.add(2.0);
        data.add(9.0);data.add(2.0);
        //m
        data.add(12.0);data.add(17.0);
        //c
        data.add(-2.76);data.add(0.0);
        data.add(-5.0);data.add(-2.24);
        data.add(-5.0);data.add(-5.0);
        //s
        data.add(2.24);data.add(-5.0);
        data.add(5.0);data.add(-5.0);
        data.add(5.0);data.add(2.24);
        data.add(5.0);data.add(5.0);
        data.add(-2.24);data.add(5.0);
        data.add(-5.0);data.add(5.0);

        Double a[] = new Double[59];

        int index = 0;
        for (Double d:data){
            a[index] = d*3+18;
            index++;
        }

        String path = "<path\n" +
                "android:fillColor=\"@color/colorIcon\"\n" +
                "android:pathData=\"M"+a[0]+","+a[1]+"\n" +
                "L"+a[2]+","+a[3]+"\n" +
                "L"+a[4]+"," +a[5]+"\n"+
                "c"+a[6]+","+a[7]+" " +
                " "+a[8]+","+a[9]+" " +
                " "+a[10]+","+a[11]+"\n" +
                "v"+a[12]+"\n" +
                "c"+a[13]+","+a[14]+" " +
                ""+a[15]+","+a[16]+" " +
                ""+a[17]+","+a[18]+"\n" +
                "h"+a[19]+"\n" +
                "c"+a[20]+","+a[21]+" " +
                ""+a[22]+","+a[23]+" " +
                ""+a[24]+","+a[25]+"\n" +
                "L"+a[26]+","+a[27]+"\n" +
                "c"+a[28]+","+a[29]+" " +
                ""+a[30]+","+a[31]+" " +
                ""+a[32]+","+a[33]+"\n" +
                "h"+a[34]+"\n" +
                "L"+a[35]+","+a[36]+"\n" +
                "L"+a[37]+","+a[38]+"\n" +
                "z\n" +
                "M"+a[39]+","+a[40]+"\n" +
                "c"+a[41]+","+a[42]+" " +
                ""+a[43]+","+a[44]+" " +
                ""+a[45]+","+a[46]+"\n" +
                "s"+a[47]+","+a[48]+" " +
                ""+a[49]+","+a[50]+" " +
                ""+a[51]+","+a[52]+" " +
                ""+a[53]+","+a[54]+" " +
                ""+a[55]+","+a[56]+" " +
                ""+a[57]+","+a[58]+"\n" +
                "z\"/>";

        Log.d("AppIconUtil", path);
    }

}
