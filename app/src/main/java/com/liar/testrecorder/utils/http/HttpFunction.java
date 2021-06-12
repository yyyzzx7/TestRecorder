package com.liar.testrecorder.utils.http;

import android.os.Build;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.RequiresApi;
import okhttp3.*;

import java.io.*;


import static android.content.ContentValues.TAG;

public class HttpFunction {
    public static final MediaType myJSON = MediaType.parse("application/json; charset=utf-8");
    public static final String myURL = "http://103.210.21.176:8080/asr";
    private  final  OkHttpClient client  = new OkHttpClient();

    public HttpFunction(){
        //client.connectTimeoutMillis();
    }



    /**POST 请求
     * 发送一个string请求
     * @throws Exception
     */
    public void SendPostString(File voice) throws Exception {



        String str =
                "//uUBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAWGluZwAAAA8AAABYAAAx+AABAwsO" +
                "EBIUFhYYGhweICIkJCYoKiwuMDIzMzU3OTs9P0FBQ0VHTVFVW1tiZmtvdHh7f3+Dio+UmZ2hoaWq" +
                "r7O3vMDAxMjLzdDS1NbW2Nrc3uDi5OTm6Ors7vDy8vT2+Pr8/v8AAAAKTEFNRTMuOTlyBEgAAAAA" +
                "AAAAABUgJAPAhQABmgAAMfgVqhnRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA//sUBAAP8AAAaQAAAAgA" +
                "AA0gAAABAAABpAAAACAAADSAAAAEDrcUCx+9QdbigWP3qQgAADOQaG6BoQAABnINDdA0ECBJSg6I" +
                "wjQl5sECBJSg6IwjQl5tTEFNRTMuOTku//sUBB4P8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/A" +
                "AAAENVVVVVVVVVVVVVVVVVVVVVVVVVVVVVUCAAegUd1AQAD0Cjur7QZ+0GVMQU1FMy45OS41VVVV" +
                "VVVVVVVV//uUBDwHcAAAf4AAAAgAAA/wAAABAMwDPqAAACAZgGfUAAAEVVVVVVVVVVVVVVVVARgA" +
                "AIVaChX/qARgAAIVaChX/qL8s7nKi/LO5ypMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVUCgAe9fSBQAPevp8nb3fFvJ293xZVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVBQCAAK7C/WGQUAgACuwv1hnEql4lUtVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVRAABTDMgIAAKYZkCaRBlSaRBlVMQU1FMy45OS41VVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVWlOUpTlIVl8rCsvlVMQU1FMy45OS41VVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVUQAAaRwgAA0jlMQU1FMy45//skBOODMEMF" +
                "TygBSJwIYKnlACkTgVADPwAAACAqAGfgAAAEOS41VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVfpf/0fS//o+j6FMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sU" +
                "BOqP8DMA0CgAAAgGYBoFAAABAFABQgAAADAKAChAAAAGVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVMQU1FMy45OS41VVVVVSAAAHAwIz/5AmQAAA4GBGf///sUBPALsE4HUEABGRgJwOoIACMj" +
                "AJgBQqAAACATAChUAAAEIEz0IQEDbInTTv/oDn66j0IQEDbInTTv/oDn66lMQU1FMy45OS41VVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVI//sUBOwLsCsA0KgAAAgFYBoVAAABAJgBPgAAACATACfAAAAEUKjv" +
                "/kUu/rSFCo7/5FLv6xIXFlf/SkSFxZX/0pVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "//sUBO0P8EQA0MAAAAgIgBoYAAABAFQBQAAAACAKgCgAAAAEVVVVVVVVVVVVVVVQAAcWhKAADi0J" +
                "j6Br9sfQNftMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOsHcC0AT6gAAAgFoAn1" +
                "AAABAHgBQKAAACAPACgUAAAEVVVVVQ4AAAC0FTyQ4AAAC0FTySQGEBHpJAYQEelMQU1FMy45OS41" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOeLsBQAz4AAAAgCgBnwAAABAGwDPqAAACANgGfUAAAE" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVa60IkShEiVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVV//sUBOYP8CMAUCgAAAgEYAoFAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVWgDUAbJZJMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOaP8B0Az4AAAAAD" +
                "oBnwAAAAACgDQAAAACAFAGgAAAAEVVVVVVVVVVVVVVVVVVVVVSAABYIyAABYI4HKByVMQU1FMy45" +
                "OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/A" +
                "AAAEVVVVVVVVVVVVVVVVVVVVVVVVQAADhmgAAHDOutVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVV//sUBPiFUEsDTygAEAgJYGnlAAIBAbAPQyAkIGA2AehkBIQMVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVUAABwagAAODaaVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBPCFUEQAz4AA" +
                "AAgIgBnwAAABAMgDQKAAACAZAGgUAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVkZFMQU1F" +
                "My45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOqHcCcA0CgAAAoE4BoFAAABQIQBQqAAADgQ" +
                "gChUAAAHVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVV//sUBO4HcDwAUUAAAAgHgAooAAABAKANPwAMYCAUAafgAYwEVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOUHcAYA" +
                "0AAAAAgAwBoAAAABAFQBQKAAACAKgCgUAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVM" +
                "QU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOSP8A8AUAAAAAgB4AoAAAABACQBQgAA" +
                "ACAEgChAAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVV//sUBOgHcCQAUKgAAAwEgAoVAAABgDgLOgAMICAHAWdAAYQEVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOcH" +
                "cCMC0KgDCAwEYFoVAGEBgBgBQgAAACADAChAAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOaHcCEA0KgAAAoEIBoVAAABQBgB" +
                "QAAAACADACgAAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVV//sUBOKP8AgATwAAAAgBAAngAAABAAAB/gAAACAAAD/AAAAEVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sU" +
                "BOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOGP8AAAf4AAAAgAAA/wAAAB" +
                "AAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVUFOAhAJyAAAf3kLYwl" +
                "zQ8L8dBCMhOc5v+QQFFPoTHg//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/AAAAEIKDm" +
                "BABiIzeWONtG4nDgJBIhHMA4/v/9AKcBCATkAAA/vIWxhLmh4X46CEZCc5zf8ggKKfQmPAQUHMCA" +
                "//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/AAAAEDERm8scbaNxOHASCRCOYBx/f/6Aq" +
                "3CCApWCABrHUADEBsJcIEM0RwjoW5Kr1t/rsb7DsXlt+AocsPUmK//sUBOGP8AAAf4AAAAgAAA/w" +
                "AAABAAAB/gAAACAAAD/AAAAEpQHUR0BwUqErAVCBIOgF25W7iE9Q1TF84ZfShllkrp9EyjwHMxCD" +
                "nAdWjGCrcIIClYIAGsdQAMQGwlwg//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/AAAAE" +
                "QzRHCOhbkqvW3+uxvsOxeW34Chyw9SYqlAdRHQHBSoSsBUIEg6AXblbuIT1DVMXzhl9KGWWSun0T" +
                "KPAc//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/AAAAEzEIOcB1aMZUa23gDIguAAfJ8" +
                "eBLzMw1Cj3V+EiCivUQMNMPDDV2KLqQTDbEo+gCIoEhQa23gDIguAAfJ//sUBOGP8AAAf4AAAAgA" +
                "AA/wAAABAAAB/gAAACAAAD/AAAAE8eBLzMw1Cj3V+EiCivUQMNMPDDV2KLqQTDbEo+gCIoEhQIgH" +
                "h5mIJDuAAyogJCzdJgeMslGOBIGb7P1M//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/A" +
                "AAAEQhsx1Czqy48VElH6GUGWvJhEA8PMxBIdwAGVEBIWbpMDxlkoxwJAzfZ+piENmOoWdWXHioko" +
                "/Qygy15N//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAAAD/AAAAECYaId3eIFaAAAlNljk9A" +
                "z7AdLF6SDl5fBj9AgH/2wxhZly0FkCAoVKOf8PBMNEO7vECtAAASmyxyegZ9//t0BP+AAbsf0Gnl" +
                "YnA3Y/oNPKxOCni3Q6ngtOFPFuh1PBacgOli9JBy8vgx+gQD/7YYwsy5aCyBAUKlHP+HhvRvrajD" +
                "crFGLxi+MOE0PVW6eiI9SkFVoiUweSN6N9bUYblYoxeMXxhwmh6q3T0RHqUgqtESmDyVgG1FljOA" +
                "BiKix61cXAQBWKBzzHikcsXPd3d0RKREQUP3DsXBIUpD+Z+RjNKj+JIBtRZYzgAYiosetXFwEAVi" +
                "gc8x4pHLFz3d3dESkREFD9w7FwSFKQ/mfkYzSo/iRgM2Z2VoWSVDoKYppEDZxmorvQHo8rBvtBO1" +
                "lXsa3Vks0FjLeIWtq9TPiRj/fsTJQ5D9n3gokZEkAUd5mtuU90EuPdkdJVjHe15W//tUBPIAAVYc" +
                "1OlgKyAqw5qdLAVkBWRbW+OYpwCsi2t8cxTgFULd/nBAwGbM7K0LJKh0FMU0iBs4zUV3oD0eVg32" +
                "gnayr2NbqyWaCxlvELW1epnxIx/v2Jkoch+z7wUSMiSAKO8zW3Ke6CXHuyOkqxjva8rCqFu/zggV" +
                "UDOFV4mU0ZAAIJh1BjQAHwfJ5NRwomiUzEN921uMrAqEAvkTRkIDRYIGLAQOYMGWBmUSF+7kTdtf" +
                "yRihoFVXYlwy8Kjs//tUBOoBEV0cVngMGEAro4rPAYMIA4QbU4A8YEBwg2pwB4wI8ZXQa8IErC2H" +
                "/5kxPKBnCq8TKaMgAEEw6gxoAD4Pk8mo4UTRKZiG+7a3GVgVCAXyJoyEBosEDFgIHMGDLAzKJC/d" +
                "yJu2v5IxQ0CqrsS4ZeFR2eMroNeECVhbD/8yYnrAGeHdVYAT8ADZOedMjKiWlIWbID+81dqb+BEW" +
                "HaOO4mqQSo3Xm8Y74afsSHMTcqvb3QqX9H//zHF1WAM8//t0BPABEXYd0+HgQoguw7p8PAhRCgxz" +
                "T8y/B+FBjmn5l+D8O6qwAn4AGyc86ZGVEtKQs2QH95q7U38CIsO0cdxNUglRuvN4x3w0/YkOYm5V" +
                "e3uhUv6P//mOLqXgTfVgACfAAWRHOqw80P2msXIctRXBfBWCsxrbBVsUzWrYYoWWcp3tJMQM8C/3" +
                "D+BN9WAAJ8ABZEc6rDzQ/aaxchy1FcF8FYKzGtsFWxTNathihZZyne0kxAzwL/cPsgJodDAABe9E" +
                "2oSVKDhdfHuhAHUA0dclE7IvGatm0HjW3oVAXpZ2Fh5FYlGndqLICaHQwAAXvRNqElSg4XXx7oQB" +
                "1ANHXJROyLxmrZtB41t6FQF6WdhYeRWJRp3ahZERVXZTBAMc//uEBO6AAo8aV3n5ZDhR40rvPyyH" +
                "B0ypa+ekVGDplS189IqMADRNwXwAZPUZIoaWsNfL24Imjm05wx4tK5xQeESnwx//VIiKq7KYIBjg" +
                "AaJuC+ADJ6jJFDS1hr5e3BE0c2nOGPFpXOKDwiU+GP/6tMyN2YzAgUPQhko9BUPwEFy9LnRdFjCb" +
                "UejifrGSkQh1pNYI4DFgrfhDBBH/pUT0zI3ZjMCBQ9CGSj0FQ/AQXL0udF0WMJtR6OJ+sZKRCHWk" +
                "1gjgMWCt+EMEEf+lRNWiAGZmIBAEHAAaDqAHUJBEygnW5IgnsB7V+UVDu8IyeD3r58SYOy0y+k4C" +
                "//rQEw9RADMzEAgCDgANB1ADqEgiZQTrckQT2A9q/KKh3eEZPB718+JMHZaZfScBf/1oCYeoRJUh" +
                "SECBBoP6IzSCfFAv+8hIzKwPdYIj6WVhBzXTsDiD//tUBPgBEXgXWmnmSegvAutNPMk9Bdhdaeew" +
                "wqC7C6089hhUigauJ3f/QJDNCJKkKQgQINB/RGaQT4oF/3kJGZWB7rBEfSysIOa6dgcQcUDVxO7/" +
                "6BIZwSB0eUIkTwAAIIMOQCYfwRJtLOQpglsVElUdLWWLVkrrSrjgpfhyiUL4JA6PKESJ4AAEEGHI" +
                "BMP4Ik2lnIUwS2KiSqOlrLFqyV1pVxwUvw5RKF4ERZ3QFOIYWGjAN3KJzGewliBAK1Rv//tkBOgB" +
                "EU0V23mGGkgportvMMNJBeBxa+ekRaC8Di189Ii0FGHdn7g7aR1tNRRThRWk8BYERZ3QFOIYWGjA" +
                "N3KJzGewliBAK1RvFGHdn7g7aR1tNRRThRWk8BWhMYRnRjYVAAAYSTsgUIIRDTybiXmTsDvYVVBi" +
                "TScXPHDyPWVCvWGqExhGdGNhUAABhJOyBQghENPJuJeZOwO9hVUGJNJxc8cPI9ZUK9Ya4l97nrj4" +
                "yXWAaJ2lOWRFVja46xsqPaxGhYYhrI/t1UsyGt4pgKLcS+9z1x8ZLrANE7SnLIiqxtcdY2VHtYjQ" +
                "sMQ1kf26//tUBPUBEYEXWnmDM7gwIutPMGZ3BUxda+YUTGCpi618womMqWZDW8UwFFlgR4VCFzv4" +
                "AAAsOSMOoFwZGwbFCxTqK8sPZXnsI54y3vdB74CwI8KhC538AAAWHJGHUC4MjYNihYp1FeWHsrz2" +
                "Ec8Zb3ug98BUCODYgUXOBon1RUok4A+5tYloIpOBQyDaS2VyxFv6csQoEcGxAoucDRPqipRJwB9z" +
                "axLQRScChkG0lsrliLf05YhQBoJyNRd/AAAy//tkBOiBEUsc23lJEOopY5tvKSIdROBZaeYJDOCc" +
                "Cy08wSGcUeCH4SpMVk8gkxM4NfNKPfve6EF3DEyDRx3xOoA0E5Gou/gAAZKPBD8JUmKyeQSYmcGv" +
                "mlHv3vdCC7hiZBo474nYCWFUiQr8BcBNwgLAmNmFSUK6PSiDgoF60ogzDvJY87NeRgJYVSJCvwFw" +
                "E3CAsCY2YVJQro9KIOCgXrSiDMO8ljzs15FQFJOFJTm4AAAgFJA1gNWeUEhzmaNJ0s4X13WPfSbR" +
                "1dVEw1AUk4UlObgAACAUkDWA1Z5QSHOZo0nSzhfXdY99JtHV1UTD//tUBP4BETwM2XnsSBAngZsv" +
                "PYkCBMxtYaekRWiZjaw09IitMARlg0VXJ3IPcBGE0xAOFJbUKpYh5uMqec6gNlMr9M/OdikZsWlC" +
                "2Qs9NxwIcPmAIywaKrk7kHuAjCaYgHCktqFUsQ83GVPOdQGymV+mfnOxSM2LShbIWem44EOH1WAl" +
                "VXZYTefQADhHPBCOhBKy8wWnjJZPk5zRa1jZOrsXs71myyqXmJ0cHxVSEYq0EplmuQs289eKQ5gD" +
                "nwjn//tEBP4BESEKV/nsMJokIUr/PYYTRBxhW+ekROCDjCt89Iic64zZZQGnCQ4QsEHhc4tsDcma" +
                "wQQwEqq7LCbz6AAcI54IR0IJWXmC08ZLJ8nOaLWsbJ1di9nes2WVS8xOjg+KqQjFWglMs1yFm3nr" +
                "xSHMAc+Ec/XGbLKA04SHCFgg8LnFtgbkzWCCGRtt960G8ABoaxBCwbFcSay13bUdVEOkUlbLhm5G" +
                "//tEBPsBES0Q13nmEcglohrvPMI5BEhDXeYkQyiJCGu8xIhlf5QvOSdOSUUMKRtJ6gJGPnZmv/5Z" +
                "AThQ7bOY2PMF0DGsjbb71oN4ADQ1iCFg2K4k1lru2o6qIdIpK2XDNyM/yheck6ckooYUjaT1ASMf" +
                "OzNf/yyAnCh22cxseYLoGNUzRnYAeFiQIAA5UYRohNYUl+aGImdEBQpXYf+rvUdzFaIYCisF//tU" +
                "BPUBERgX1/lpEGojAvr/LSINRdR5WeYM0SC6jys8wZokzle2Md/NjS/RaO3/58LzGaM7ADwsSBAA" +
                "HKjCNEJrCkvzQxEzogKFK7D/1d6juYrRDAUVgucr2xjv5saX6LR2//PheaZqYCYp2wXqpEQmJLTR" +
                "+NoIUCEwMFSB0cQf+GOoZX5g+ir8P3ZGayv1didykzUwExTtgvVSIhMSWmj8bQQoEJgYKkDo4g/8" +
                "MdQyvzB9FX4fuyM1lfq7E7lF//uEBPEAApEs1nmJFwhSJZrPMSLhB8S5VaYNK8D4lyq0waV4C6yq" +
                "m6lIQWQAP+GcSQibNSh/B6y1qUQWXLCx1zFLxp4zYEcSv/qX8LpnQ8FG0jgusqpupSEFkAD/hnEk" +
                "ImzUofwestalEFlywsdcxS8aeM2BHEr/6l/C6Z0PBRtI4Ly6zbmWZIkAAwfZTJmZ6VBsQFAEWHWA" +
                "heGYWeZhew/yzhYeqQqj16WCLU/+EtdzEULjmWW9qM4IQQXl1m3MsyRIABg+ymTMz0qDYgKAIsOs" +
                "BC8Mws8zC9h/lnCw9UhVHr0sEWp/8Ja7mIoXHMst7UZwQgkP7v3d7JzDkAAgFrhom8195Gtbod2y" +
                "OyKqoXcoQv/1NFdztI/2w1ErMC2yzyy2LkwqH937u9k5hyAAQC1w0Tea+8jWt0O7ZHZFVULuUIX/" +
                "6miu52kf7YaiVmBb//tkBPaBEaEeVnkhSXI0I8rPJCkuRYQrX8SERsiwhWv4kIjZZZ5ZbFyYVDPz" +
                "+3tt4AogAEhcAyo4mIiARRJhAoiA4n2iISLZUkU06BGf0rMCHQnI7mKrfVaSCO6fT8EqAz8/t7be" +
                "AKIABIXAMqOJiIgEUSYQKIgOJ9oiEi2VJFNOgRn9KzAh0JyO5iq31Wkgjun0/BKhDu3+7fmKArAA" +
                "P9CApDKNdImHUI3TiZSZyZ7O9a+4XboBK9+r4tzuRNesuB60mVtUgBh3b/dvzFAVgAH+hAUhlGuk" +
                "TDqEbpxMpM5M9netfcLt0Ale/V8W53Im//tkBPwAAXYn2XkhHEAuxPsvJCOIBzS1aeAkYYDmlq08" +
                "BIwwvWXA9aTK2qQAw/t693bhkOnGwoeKii1WcJSGbCFZA3RVCno6aOj/OWjKUv0Qdv87lVb7P73w" +
                "xGUP7evd24ZDpxsKHiootVnCUhmwhWQN0VQp6Omjo/zloylL9EHb/O5VW+z+98MRlQ/e7+7KiBLU" +
                "AAQGCxMTCDSIFkTJJ1WVWNorUp9tBmZf/QWDBKagwvxYyC7a1oJQ6FAAH73f3ZUQJagACAwWJiYQ" +
                "aRAsiZJOqyqxtFalPtoMzL/6CwYJTUGF+LGQXbWtBKHQoAAy//tkBPmAAYcc3XgMGGAw45uvAYMM" +
                "BrC1ceAkQcDWFq48BIg48rLu4TQ4AOBGDRyl0CUNhy1VKFSfKfRIJ/Ijz0oshiN3yYu+TR3qLBl5" +
                "WXdwmhwAcCMGjlLoEobDlqqUKk+U+iQT+RHnpRZDEbvkxd8mjvUWDMrLyshoDqwAJDDROkfollYN" +
                "e937g5qT8BKN/tDflpf+AAjXkhehdF+4MysvKyGgOrAAkMNE6R+iWVg173fuDmpPwEo3+0N+Wl/4" +
                "ACNeSF6F0X7gyruaqoRMSwABcIRSIU5VRV1RLSuVOtGFNYyO5lKLZb+wCVf/8NPX//tkBPkBEY0X" +
                "3PkmEtAxovufJMJaBditc+AkQci7Fa58BIg5v5EkGVdzVVCJiWAALhCKRCnKqKuqJaVyp1owprGR" +
                "3MpRbLf2ASr//hp69/IklcioqZmmJBKwABTCKibjjUyGKB4M9lq8SLHLnb1lUMnePKDj2p/yJWdK" +
                "9KMioqZmmJBKwABTCKibjjUyGKB4M9lq8SLHLnb1lUMnePKDj2p/yJWdK9KMp6iriWIGT4ACVCI0" +
                "NxTizwUyfm20fcNlTRGLV8JBEyljIvTmphzc3KHR3jxEEL/im+AlBt31IKjMp6iriWIGT4ACVCI0" +
                "NxTi//tUBP6AAY8XXPgJGGAx4uufASMMBSRdacAkYICki604BIwQzwUyfm20fcNlTRGLV8JBEylj" +
                "IvTmphzc3KHR3jxEEL/im+AlBt31IKjFMUeIqWQmExgAOfEw7y1kaWs3Bob+oVMXipBBsUXqC2UX" +
                "EhuS7FNDUxIzCGdd//xhYxR4ipZCYTGAA58TDvLWRpazcGhv6hUxeKkEGxReoLZRcSG5LsU0NTEj" +
                "MIZ13//GFlI6qrhjIhcc+IpVmthBsjSp//tUBPGAAU4b2vgJGCApw3tfASMEBUyRZ+AkQICpkiz8" +
                "BIgQLMmFzKeEoevWc/zutHW2fN9WcwLpa6S+cUjqquGMiFxz4ilWa2EGyNKksyYXMp4Sh69Zz/O6" +
                "0dbZ831ZzAulrpL5xaE7uspjMA8AACx3UVi1RrDqhr5S4HHrLmGiSzl2rIZuaYgoIwUjWxGmhO7r" +
                "KYzAPAAAsd1FYtUaw6oa+UuBx6y5hoks5dqyGbmmIKCMFI1sRpwHm8zHRUFe//tkBOsAAVYRWPni" +
                "GeAqwisfPEM8ByBvYe0kZ0DkDew9pIzowjDsVAcGSo6D8idC0jImgDmltpLxDh1L+bQ8KgsOWmat" +
                "OA83mY6Kgr2EYdioDgyVHQfkToWkZE0Ac0ttJeIcOpfzaHhUFhy0zVpVsGq5rIQwDwAAHpQsBCAg" +
                "MwtqLLjZcoxMkccZ3eK7/fmVb5vx9SNGQog1//pVaOLWDVc1kIYB4AAD0oWAhAQGYW1FlxsuUYmS" +
                "OOM7vFd/vzKt834+pGjIUQa//0qtHFsI6vMqVMg0DkPInAbF1gCIwiIXNDLBrO0pFKcVrtYce3q1" +
                "//tkBO0BEYQY2HnpGfAwgxsPPSM+BThhZ+eMzMCnDCz88ZmYLMhz/3hHV5lSpkGgch5E4DYusARG" +
                "ERC5oZYNZ2lIpTitdrDj29WpZkOf+5AmusxzMA8AACyRkKYDdiyEQmn02ImG0lyB0idCyOEHCkVz" +
                "ihwYxOoOp/BxeDlcgTXWY5mAeAABZIyFMBuxZCITT6bETDaS5A6ROhZHCDhSK5xQ4MYnUHU/g4vB" +
                "yuyeMzMpEIPCdHMfhex6lKqIwKiQYjqclLWozjky5Syz/u40DNdSG6rJ4zMykQg8J0cx+F7HqUqo" +
                "jAqJBiOpyUtajOOT//tUBPiBEUYbWvnmEjgow2tfPMJHBRxfb+ekYWCji+389IwsLlLLP+7jQM11" +
                "IbqVgFiqm4MiBwAALqAmOxyPjUshi1b8Y44l0j7a58mQgEyEb2ixYaKpy6nUBiAWKqbgyIHAAAuo" +
                "CY7HI+NSyGLVvxjjiXSPtrnyZCATIRvaLFhoqnLqdQGMBbu6pUIA8LhEPROFTAseN1tX57txikkM" +
                "eyKxj8CAJtqDD+awFu7qlQgDwuEQ9E4VMCx43W1fnu3G//tUBPSBEXsb2vnsMDgvY3tfPYYHBNQx" +
                "beYtIgiahi28xaRBKSQx7IrGPwIAm2oMP5rCmsq7dDAHAAAPQHD8E4aiARFaYrJZBaOGoimCBuWV" +
                "mZpe5GBAtyMICeFA3hPCmsq7dDAHAAAPQHD8E4aiARFaYrJZBaOGoimCBuWVmZpe5GBAtyMICeFA" +
                "3hOkarq6gzQXDQOiIHgh4Pq51a1J/rMTeRqfesJqHvBnFgsGXLBHopGq6uoM0Fw0DoiB4IeD//tk" +
                "BOyBEXUbWfnpGNguo2s/PSMbBPBVa+eYZyCeCq188wzk6udWtSf6zE3kan3rCah7wZxYLBlywR6F" +
                "pGq7upRCDwAAIGNF44Qlh5yjrTjN2IKC9U1hNScQK59jzWQOgereutNI1Xd1KIQeAABAxovHCEsP" +
                "OUdacZuxBQXqmsJqTiBXPseayB0D1b11pghiHiHIgRRV4dwiLHFlc7xw2Wx7BHpWXstNPMqwA/L2" +
                "FmWToOJ1qCGIeIciBFFXh3CIscWVzvHDZbHsEelZey008yrAD8vYWZZOg4nWleHgANNkaAABz1P1" +
                "Qkl1Wut6xJsQ//tUBPwBEVoVWPmGGcgrQqsfMMM5BHhfZeYEbwCPC+y8wI3g6i1g7QsTxZoJYl6Q" +
                "1AvDwAGmyNAAA56n6oSS6rXW9Yk2IdRawdoWJ4s0EsS9IagUUISKA8JTmZ8P0XkvyhpnG4LdQKEJ" +
                "FAeEpzM+H6LyX5Q0zjcFuoU5BIMAABSkgHMadYz6gc1nIJBgAAKUkA5jTrGfUDmtjFwtYcb/5c0T" +
                "DJwCF2MXC1hxv/lzRMMnAIXVOAA+RkAADBQcBcW2//tUBPsBEWgX2XmMGDgtAvsvMYMHBLhTZ+YM" +
                "SYCXCmz8wYkwxlDPLFhsCBMQmSJ+n1UASJYIrlca1k53G1zgAPkZAAAwUHAXFtsZQzyxYbAgTEJk" +
                "ifp9VAEiWCK5XGtZOdxtcUAAqQyBZS6+k1Dhx7YAav+kUAAqQyBZS6+k1Dhx7YAav+kgAFZAADMD" +
                "Saa07nDL4gQc2MzJAAKyAAGYGk01p3OGXxAg5sZmQARmmFZPLiZ0+cGgAjNMKyeXEzp8//tUBPYB" +
                "EUcYWflMGEgo4ws/KYMJBNhzW+YMaUibDmt8wY0p4NVMQU1FMy45OQEBDAD6X/RZnDAICGAH0v+i" +
                "zOGAEIBOnlQBCATp5VVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVUwAAXLSYAALlp3qP3eo/VMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVUGQAIAU6FD" +
                "//wKJQZAAgBToUP//Aol//s0BPQBEQ8UVWngQbgh4oqtPAg3AlxTQyQEZehLimhkgIy9LI6CyOhM" +
                "QU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBPUFUHsC0MgP" +
                "EAAPYFoZAeIAAagPPqAwAGA1AefUBgAMVVVVVVVVVVVVVVVVVdGiqpVMQU1FMy45OS41VVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//s0BPYBES4Z0VDgSjolwzoqHAlHQig/QSKAaQBF" +
                "B+gkUA0gVVVVVVVVVVVVVVVVVVWqmqlMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "//sUBPUDMJUQUEigGpgSogoJFANTAWQBQQAAACAsgCggAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBPEDME0PUMAgE5gJoeoY" +
                "BAJzALADRQAAACAWAGigAAAEVVVVVVVVVVVVVVVVVVVVVVVV9TaP/Lp9TaP/LpVMQU1FMy45OS41" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOkP8CUAUKgAAAgEoAoVAAABAFgDQAAAACgLAGgAAAAF" +
                "VVVVVVVVVVVVVVVVVVVV27X07dr6YewoXh7ChdVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVV//sUBO8P8FgD0cABGAwLAHo4ACMBgFADQAAAACAKAGgAAAAEVVVVVVVVVVVVAgAHXuMgQADr" +
                "3Gfa8Op1e14dTqVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOGP8AAAf4AAAAgA" +
                "AA/wAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVfnr8t89fllMQU1FMy45" +
                "OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOOP8AcAUAAAAAgA4AoAAAABABgDQAAAACADAGgA" +
                "AAAEVQAQIAD7UcUDIAIEAB9qOKBkCWmXjugCWmXjuhVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVV//sUBOMP8AwAz4AAAAgBgBnwAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVBgAHzwT2awYA" +
                "B88E9mv8h//9P5D//6VMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOGP8AAAf4AA" +
                "AAgAAA/wAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVLvdvpS73b6fzH5hVMQU1F" +
                "My45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOGP8AAAf4AAAAgAAA/wAAABAAAB/gAAACAA" +
                "AD/AAAAEVVVVVVVVVVVVVVVVVVVVVVUAgAe7rGAEAD3dYxVMQU1FMy45OS41VVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVV//sUBOcP8AAAf4AAAAgAAA/wAAABAKgDPAAAACAVAGeAAAAEVVVVVVVVVVVVVVVV" +
                "VQEAB/EckAgAP4jkv2r/atVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOiLsBkA" +
                "z4AAAAgDIBnwAAABAHADQKAAACAOAGgUAAAEVVVVVVVVVVVVVVVVVVVVVVVVVf6P6Ef0vSj+l6VM" +
                "QU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOuLsCsAUCgAAAgFYAoFAAABAIwBPAAA" +
                "ACARgCeAAAAEVVVVVVVVVVVVVVVVVVVVVVVV9vlfb5X2I5b2I5ZMQU1FMy45OS41VVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVV//sUBOUP8BsATwAAAAgDYAngAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVRej5QXo+UVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBO+D" +
                "MEMAUEAAAAgIYAoIAAABAKgDQKAAACAVAGgUAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVf+j" +
                "/oVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBO0LsDgAz6gAAAgHABn1AAABAIwD" +
                "PgAAAAARgGfAAAAAVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVXI5FVMQU1FMy45OS41VVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVV//sUBOeP8CAAzwAAAAgEABngAAABADQDPgAAACAGgGfAAAAEVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVMQU1FMy45OS41VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sU" +
                "BOeP8C0A0CgAAAgFoBoFAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOmLsC0AT6gAAAgFoAn1AAAB" +
                "AEABPgAAACAIACfAAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVV//sUBOaLsAwA0AAAAAgBgBoAAAABAGwDQKAAACANgGgUAAAEVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "//sUBOcP8BQAz4AAAAgCgBnwAAABAGADPAAAACAMAGeAAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOULsAAAf4AAAAgAAA/w" +
                "AAABAGgDQKAAACANAGgUAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOOP8A4AUAAAAAgBwAoAAAABAAAB/gAAACAAAD/AAAAE" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVV//sUBOMP8AAAf4AAAAgAAA/wAAABACQBPgAAACAEgCfAAAAEVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//sUBOGP8AAAf4AAAAgA" +
                "AA/wAAABAAAB/gAAACAAAD/AAAAEVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV" +
                "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
//        Base64 base64 = new Base64();
//        String voiceBase64 = base64.encodeToString(str.getBytes("UTF-8"));

        long fileSize = voice.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return ;
        }
        FileInputStream fileInputStream = new FileInputStream(voice);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fileInputStream.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + voice.getName());
        }
        fileInputStream.close();

//        System.out.println("voive byte array!!!!!!!!!:");
//        for(byte temp:buffer){
//            System.out.print(temp);
//        }

        // 转成base64输出
        String voiceBase64 = Base64.encodeToString(buffer, Base64.DEFAULT);

//        System.out.println("base64:" + voiceBase64);

        Request request = new Request.Builder()
                .url(myURL)
                .post(RequestBody.create(myJSON, voiceBase64))
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });

//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful())
//            throw new IOException("Unexpected code " + response);
//
//        System.out.println(response.body().string());

    }





//    /**POST 请求
//     * 发送一个From请求
//     * @throws Exception
//     */
//    public void SendPostFrom() throws Exception {
//
//        RequestBody body = new FormBody.Builder()
//                .add("name", "sy")//添加参数体
//                .add("age", "18")
//                .build();
//
//        Request request = new Request.Builder()
//                .post(body) //请求参数
//                .url("http://123.207.70.54:8080/SpringMvc/hello")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful())
//            throw new IOException("Unexpected code " + response);
//    }


//    /**
//     * 发送一个表单请求
//     * @throws Exception
//     */
//    public void SendForm() throws Exception {
//        RequestBody formBody = new FormBody.Builder()
//                .add("search", "Jurassic Park")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://en.wikipedia.org/w/index.php")
//                .post(formBody)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful())
//            throw new IOException("Unexpected code " + response);
//
//        System.out.println(response.body().string());
//    }

//    /**Get请求
//     * 发送一个From请求
//     * @throws Exception
//     */
//    public void SendGetFrom() throws Exception {
//
//        Request request = new Request.Builder()
//                .get() //请求参数
//                .url("http://123.207.70.54:8080/SpringMvc/hello")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful())
//            throw new IOException("Unexpected code " + response);
//    }
}
