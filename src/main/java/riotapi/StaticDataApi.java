package riotapi;

/*
 * Copyright 2014 Taylor Caldwell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import constant.staticdata.ChampData;
import constant.staticdata.ItemData;
import constant.staticdata.ItemListData;
import constant.staticdata.RuneData;
import constant.staticdata.MasteryData;
import constant.staticdata.RuneListData;
import constant.staticdata.MasteryListData;
import constant.staticdata.SpellData;
import dto.Static.Champion;
import dto.Static.ChampionList;
import dto.Static.GameMapList;
import dto.Static.Item;
import dto.Static.ItemList;
import dto.Static.LanguageStrings;
import dto.Static.Mastery;
import dto.Static.MasteryList;
import dto.Static.Realm;
import dto.Static.Rune;
import dto.Static.RuneList;
import dto.Static.SummonerSpell;
import dto.Static.SummonerSpellList;

final class StaticDataApi {
	
	private static final String VERSION = "/v1.2/";
	private static final String STATIC_DATA_ENDPOINT = "https://global.api.pvp.net/api/lol/static-data/";

    public static ChampionList getDataChampionList(String region, String key, String locale, String version, boolean dataById, ChampData... champData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "champion?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        url += "dataById=" + dataById + "&";
        if (champData[0] != null) {
            url += "champData=";
            for(ChampData data : champData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        ChampionList championList = null;

        try {
            championList = new Gson().fromJson(Request.execute(url), ChampionList.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return championList;
    }

    public static Champion getDataChampion(String region, String key, int id, String locale, String version, boolean dataById, ChampData... champData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "champion/" + id + "?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        url += "dataById=" + dataById + "&";
        if (champData[0] != null) {
            url += "champData=";
            for(ChampData data : champData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        Champion champion = null;

        try {
            champion = new Gson().fromJson(Request.execute(url), Champion.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return champion;
    }

    public static ItemList getDataItemList(String region, String key, String locale, String version, ItemListData... itemListData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "item?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (itemListData[0] != null) {
            url += "itemListData=";
            for(ItemListData data : itemListData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        ItemList itemList = null;

        try {
            itemList = new Gson().fromJson(Request.execute(url), ItemList.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return itemList;
    }

    public static Item getDataItem(String region, String key, int id, String locale, String version, ItemData... itemData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "item/" + id + "?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (itemData[0] != null) {
            url += "itemData=";
            for(ItemData data : itemData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        Item item = null;

        try {
            item = new Gson().fromJson(Request.execute(url), Item.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return item;
    }

    public static Realm getDataRealm(String region, String key) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION +  "realm?api_key=" + key;

        Realm realm = null;

        try {
            realm = new Gson().fromJson(Request.execute(url), Realm.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return realm;
    }

    public static RuneList getDataRuneList(String region, String key, String locale, String version, RuneListData... runeListData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "rune?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (runeListData[0] != null) {
            url += "runeListData=";
            for(RuneListData data : runeListData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        RuneList runeList = null;

        try {
            runeList = new Gson().fromJson(Request.execute(url), RuneList.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return runeList;
    }

    public static Rune getDataRune(String region, String key, int id, String locale, String version, RuneData... runeData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "rune/" + id + "?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (runeData[0] != null) {
            url += "runeData=";
            for(RuneData data : runeData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        Rune rune = null;

        try {
            rune = new Gson().fromJson(Request.execute(url), Rune.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return rune;
    }

    public static MasteryList getDataMasteryList(String region, String key, String locale, String version, MasteryListData... masteryListData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "mastery?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (masteryListData[0] != null) {
            url += "masteryListData=";
            for(MasteryListData data : masteryListData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        MasteryList masteryList = null;

        try {
            masteryList = new Gson().fromJson(Request.execute(url), MasteryList.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return masteryList;
    }

    public static Mastery getDataMastery(String region, String key, int id, String locale, String version, MasteryData... masteryData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "mastery/" + id + "?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (masteryData[0] != null) {
            url += "masteryData=";
            for(MasteryData data : masteryData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        Mastery mastery = null;

        try {
            mastery = new Gson().fromJson(Request.execute(url), Mastery.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return mastery;
    }

    public static SummonerSpellList getDataSummonerSpellList(String region, String key, String locale, String version, boolean dataById, SpellData... spellData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "summoner-spell?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        url += "dataById=" + dataById + "&";
        if (spellData[0] != null) {
            url += "spellData=";
            for(SpellData data : spellData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        SummonerSpellList spellList = null;

        try {
            spellList = new Gson().fromJson(Request.execute(url), SummonerSpellList.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return spellList;
    }

    public static SummonerSpell getDataSummonerSpell(String region, String key, int id, String locale, String version, SpellData... spellData) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "summoner-spell/" + id + "?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        if (spellData[0] != null) {
            url += "spellData=";
            for(SpellData data : spellData) {
            	url += data.getName() + ",";
            }
            url += "&";
        }
        url += "api_key=" + key;

        SummonerSpell spell = null;

        try {
            spell = new Gson().fromJson(Request.execute(url), SummonerSpell.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return spell;
    }

    public static List<String> getDataVersions(String region, String key) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "versions?api_key=" + key;

        List<String> versions = null;

        try {
            versions = new Gson().fromJson(Request.execute(url), new TypeToken<List<String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return versions;
    }
    
    public static List<String> getDataLanguages(String region, String key) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "languages?api_key=" + key;

        List<String> languages = null;

        try {
            languages = new Gson().fromJson(Request.execute(url), new TypeToken<List<String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return languages;
    }
    
    public static GameMapList getDataGameMapList(String region, String key, String locale, String version) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "map?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        url += "api_key=" + key;

        GameMapList gameMapList = null;

        try {
        	gameMapList = new Gson().fromJson(Request.execute(url), GameMapList.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return gameMapList;
    }
    
    public static LanguageStrings getDataLanguageStrings(String region, String key, String locale, String version) throws RiotApiException {

        String url = STATIC_DATA_ENDPOINT + region + VERSION + "language-strings?";

        if (locale != null) {
            url += "locale=" + locale + "&";
        }
        if (version != null) {
            url += "version=" + version + "&";
        }
        url += "api_key=" + key;

        LanguageStrings languageStrings = null;

        try {
        	languageStrings = new Gson().fromJson(Request.execute(url), LanguageStrings.class);
        } catch (JsonSyntaxException e) {
            throw new RiotApiException(RiotApiException.PARSE_FAILURE);
        }

        return languageStrings;
    }
    
}
