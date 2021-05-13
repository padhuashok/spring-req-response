package com.galvanize.springreqresponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TextService {

    static String toProperCase(String s) {
        String temp=s.trim();
        String spaces="";
        if(temp.length()!=s.length())
        {
            int startCharIndex=s.charAt(temp.indexOf(0));
            spaces=s.substring(0,startCharIndex);
        }
        temp=temp.substring(0, 1).toUpperCase() +
                spaces+temp.substring(1).toLowerCase()+" ";
        return temp;

    }

    static String toCamelCase(String s){
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts){
            if(part!=null && part.trim().length()>0)
                camelCaseString = camelCaseString + toProperCase(part);
            else
                camelCaseString=camelCaseString+part+" ";
        }
        return camelCaseString;
    }

    @RequestMapping(value = "/camelize", method = GET)
    public String toCamelize(@RequestParam String original,@RequestParam(required = false) boolean initialCap ){
        String[] origArr = original.split("_");
        int index = initialCap ? 0 : 1;
        String response = !initialCap ? origArr[0] : "";
        for(int i = index ; i< origArr.length ; i++){
            origArr[i] = origArr[i].substring(0, 1).toUpperCase()+origArr[i].substring(1);
            response += origArr[i];
        }
        return response;
    }

}
