package com.example.aftest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AFUtil {
    public static List<Promotion> getPromotionDetails(String serverResponse) throws JSONException {
        List<Promotion> promotions = new ArrayList<>();
        JSONObject response = new JSONObject(serverResponse);
        JSONArray array = response.getJSONArray("promotions");
        for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            Promotion promotion = new Promotion();
            promotion.setDescription(object.getString("description"));
            if(object.has("footer"))
                promotion.setFooter(object.getString("footer"));
            promotion.setImage(object.getString("image"));
            promotion.setTitle(object.getString("title"));
            try {
                promotion.setButtonTarget(object.getJSONObject("button").getString("target"));
                promotion.setButtonTitle(object.getJSONObject("button").getString("title"));
            }catch (Exception e){
                promotion.setButtonTarget(object.getJSONArray("button").getJSONObject(0).getString("target"));
                promotion.setButtonTitle(object.getJSONArray("button").getJSONObject(0).getString("title"));
            }
            promotions.add(promotion);
        }

        return promotions;
    }
}
