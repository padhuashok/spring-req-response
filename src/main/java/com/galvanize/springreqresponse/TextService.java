package com.galvanize.springreqresponse;

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
        StringBuilder response = new StringBuilder(!initialCap ? origArr[0] : "");
        for(int i = index ; i< origArr.length ; i++){
            origArr[i] = origArr[i].substring(0, 1).toUpperCase()+origArr[i].substring(1);
            response.append(origArr[i]);
        }
        return response.toString();
    }

    @RequestMapping(value = "/redact", method = GET)
    public String replaceWords(@RequestParam String original, @RequestParam List<String> badWord){
        String[] originalArr = original.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word:originalArr) {
            StringBuilder stars = new StringBuilder();
            for (String item : badWord) {
                if(item.equals(word)){
                    stars.append("*".repeat(item.length()));
                    break;
                }
            }
            if (stars.length()>0)
                result.append(stars).append(" ");
            else
                result.append(word).append(" ");
        }
        return result.toString().trim();
    }

    @RequestMapping(value = "/encode" ,method = POST)
    public String encodeString(@RequestParam String message, @RequestParam String key){
        StringBuilder encodedString = new StringBuilder();
        Map<String,String> letterPair = new HashMap<>();
        String[] keyArr = key.split("");
        String[] letterArr = {"a","b","c","d","e","f","g","h","i","j"
                ,"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for(int i = 0;i < letterArr.length;i++){
            letterPair.put(letterArr[i],keyArr[i]);
        }
        letterPair.put(" "," ");
        for (char c : message.toCharArray())
        {
            encodedString.append(letterPair.get("" + c));
        }
        return encodedString.toString();
    }

    @PostMapping("/s/{find}/{replace}")
    public String getRawString(@PathVariable String find,
                               @PathVariable String replace,
                               @RequestBody String search) {

        return search.replace(find, replace);
    }
}
