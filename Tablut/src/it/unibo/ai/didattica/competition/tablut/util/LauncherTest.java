package it.unibo.ai.didattica.competition.tablut.util;

import java.io.IOException;

import it.unibo.ai.didattica.competition.tablut.client.TablutRandomBlackClient;
import it.unibo.ai.didattica.competition.tablut.server.Server;
import it.unibo.ai.didattica.competition.tablut.tablutai.client.TablutAIClient;

public class LauncherTest {

	public static void main(String[] args) {
		String[] arrayClientB = new String[]{"BLACK"};
		String[] arrayServer = new String[] {"-g"};
		String[] arrayAlgiseW = new String[] {"WHITE", "60", "localhost"};
		
		Server.main(arrayServer);
		try {
			TablutRandomBlackClient.main(arrayClientB);
			TablutAIClient.main(arrayAlgiseW);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
