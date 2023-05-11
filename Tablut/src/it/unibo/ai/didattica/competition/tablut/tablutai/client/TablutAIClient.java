package it.unibo.ai.didattica.competition.tablut.tablutai.client;

import java.io.IOException;
import java.net.UnknownHostException;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.tablutai.heuristic.TablutAIAlphaBeta;

public class TablutAIClient extends TablutClient {

	public TablutAIClient(String player, String name, int timeout, String ipAddress) 
			throws UnknownHostException, IOException {
		super(player, name, timeout, ipAddress);
	}

	public static void main(String[] args) throws IOException {
		String name = "TablutAI"; 
		if (args.length != 3) {
			System.out.println("USAGE: lo script dev'essere invocato nel seguente modo: ./runmyplayer.sh <WHITE|BLACK> <timeout> <ip_server>");
			System.exit(-1);
		} else {
			String player = args[0] ;
			int timeout = Integer.parseInt(args[1]);
			String ipServer = args[2];
			System.out.println(timeout);
			TablutAIClient client = new TablutAIClient(player, name, timeout, ipServer);
			client.run();
		}
		
	}

	@Override
	public void run() {
		// Inviamo al server il nome del gruppo
		try {
			this.declareName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//saluta
		this.saluta();
		
		// Il bianco deve fare la prima mossa
		State state = new StateTablut();
		state.setTurn(State.Turn.WHITE);
		GameAshtonTablut gameRules = null;
		
		// Impostiamo le regole del gioco
		if(this.getPlayer().equals(State.Turn.WHITE)){
			gameRules = new GameAshtonTablut(99, 0, "logs", this.getName(), "blackOpponent");
		}
		else {
			gameRules = new GameAshtonTablut(99, 0, "logs", "whiteOpponent", this.getName());
		}

		while (true) {
			// Recuperiamo lo stato dal server
			try {
				this.read();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}

			// Se sono WHITE
			if (this.getPlayer().equals(State.Turn.WHITE)) {

				// Se è il mio turno (WHITE)
				if (state.getTurn().equals(StateTablut.Turn.WHITE)) {

					System.out.println("\n Cercando la prossima mossa... ");

                    // Serchiamo la mossa migliore
                    Action a = findBestMove(gameRules, state);
                    
                    System.out.println("\nAzione selezionata: " + a.toString());
                    try {
                    	this.write(a);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
					 
				}

				// Se è il turno dell'avversario (BLACK)
				else if (state.getTurn().equals(StateTablut.Turn.BLACK)) {
					System.out.println("Aspettando la mossa dell'avversario...\n");
				}
				// Se vinco
				else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					System.out.println("YOU WIN!");
					System.exit(0);
				}
				// Se perdo
				else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					System.out.println("YOU LOSE!");
					System.exit(0);
				}
				// Se pareggio
				else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					System.out.println("DRAW!");
					System.exit(0);
				}

			}
			// Se sono BLACK 
			else {

				// Se è il mio turno (BLACK)
				if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {

					System.out.println("\n Cercando la prossima mossa... ");

					//Cerchiamo la mossa migliore
					Action a = findBestMove(gameRules, state);

					System.out.println("\nAzione selezionata: " + a.toString());
					try { 
						this.write(a);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					
				}

				// Turno dell'avversario (WHITE)
				else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
					System.out.println("Aspettando la mossa dell'avversario...\n");
				}

				// Se perdo
				else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
					System.out.println("YOU LOSE!");
					System.exit(0);
				}

				// Se vinco
				else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
					System.out.println("YOU WIN!");
					System.exit(0);
				}

				// Se pareggio
				else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
					System.out.println("DRAW!");
					System.exit(0);
				}
			}
		}

	}
	
	public void saluta() {
		System.out.println("                                                                                                    \r\n"
				+ " .::::::::::::::::::::::::                                                                        \r\n"
				+ " .:::::::::::..:::::::::::                                                                         \r\n"
				+ " .:::::::::..:..::::::::::  ........   ...     .......   ..      ..    ..  ........   ..      ..   \r\n"
				+ " .::.:::::..::::.:::::::::  ::::::::  .:::     ::::::::  ::      :^.   :^. ::::::::  .:::    .^:   \r\n"
				+ " .:: ......::::::.......::     ::     ::::.    ::   .::  ::      ::.   ::.    ::     ::::.   .::   \r\n"
				+ " .::.:::.....::.....:::.::     ::    .:. ::    ::::::::  ::      ::.   ::.    ::    ::..::   .::   \r\n"
				+ " .::.::.:::......::..:..::     ::    ::..:::   ::.  .::  ::      ::.   ::.    ::   .::..::.  .::   \r\n"
				+ " .::........::::........::     ::   .::.:.::.  ::.  .::  ::.     ::.  .::.    ::   :::..:::  .::   \r\n"
				+ " .::.    ..........    .::     ::   ::    .:.  ::::::.   ::::::.  .:::::.     ::  .::    .:. .::   \r\n"
				+ " .::..::::::::::::::::..::                                                                         \r\n"
				+ " .::::::::::::::::::::::::                                                                         \r\n"
				+ " :::::::::::::::::::::::::                                                                          \r\n"
				+ "                                                                                                   \r\n");
	}

	private Action findBestMove(GameAshtonTablut gameRules, State state) {
		TablutAIAlphaBeta search = new TablutAIAlphaBeta(gameRules, Double.MIN_VALUE, Double.MAX_VALUE, this.timeout-2);
		return search.makeDecision(state);
	}

}
