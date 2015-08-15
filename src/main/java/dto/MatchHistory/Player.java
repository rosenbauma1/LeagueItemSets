package dto.MatchHistory;

import java.io.Serializable;

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

/**
 * @deprecated  As of release 3.6.0
 */
public class Player implements Serializable {

	private static final long serialVersionUID = -7451446329752097677L;
	private String matchHistoryUri, summonerName;
	private int profileIcon;
	public long getSummonerId() {
		return summonerId;
	}

	private long summonerId;
	
	public String getMatchHistoryUri() {
		return matchHistoryUri;
	}
	
	public String getSummonerName() {
		return summonerName;
	}
	
	public int getProfileIcon() {
		return profileIcon;
	}
	
}
