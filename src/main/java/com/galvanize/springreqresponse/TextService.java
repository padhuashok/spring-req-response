package com.galvanize.springreqresponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TextService {

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

    @RequestMapping(value = "/redact", method = GET)
    public String replaceWords(@RequestParam String original, @RequestParam List<String> badWord){
        String[] originalArr = original.split(" ");
        String result = "";
        for (String word:originalArr) {
            String stars = "";
            for (String item : badWord) {
                if(item.equals(word)){
                    for (int i = 0; i < item.length(); i++) {
                        stars += "*";
                    }
                    break;
                }
            }
            if (stars.length()>0)
                result+=stars + " ";
            else
                result+=word + " ";
        }
        return result.trim();
    }

    @RequestMapping(value = "/encode" ,method = POST)
    public String encodeString(@RequestParam String message, @RequestParam String key){
        String encodedString ="";
        Map<String,String> letterPair = new HashMap<String,String>();
        String[] keyArr = key.split("");
        String[] letterArr = {"a","b","c","d","e","f","g","h","i","j"
                ,"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for(int i = 0;i < letterArr.length;i++){
            System.out.println("key and value in map==" + letterArr[i] + " "+ keyArr[i]);
            letterPair.put(letterArr[i],keyArr[i]);
        }
        letterPair.put(" "," ");
        for (char c : message.toCharArray())
        {
            System.out.println("character "+c);
            encodedString += letterPair.get(""+c);
        }
        return encodedString;
    }

    @PostMapping("/s/{find}/{replace}")
    public String getRawString(@PathVariable String find,
                               @PathVariable String replace,
                               @RequestBody String search) {
        String response = search.replace(find, replace);

        return response;
    }
}
