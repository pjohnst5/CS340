package shared.configuration;

public class DefaultConfiguration implements IConfiguration {

    @Override
    public void load(IConfigurationManager manager){
        // Server
        manager.add("port", 9000);
        manager.add("max_connections", 10);
        manager.add("protocol", "http");
        manager.add("server_host", "10.0.2.2");

        // Server Endpoints
        manager.add("execute_command_endpoint", "/api/exec");

        // Polling
        manager.add("poller_interval", 1000);

        // Game Resources
        manager.add("board_width", 1600);
        manager.add("board_height", 1088);

        // Facades
        manager.add("server_facade_name", "server.facade.ServerFacade");
        manager.add("client_facade_name", "client.facade.ClientFacade");

        // Client Facade Methods
        manager.add("client_set_user_method","setUser");
        manager.add("client_set_games_method","setGames");
        manager.add("client_create_game_method","createGame");
        manager.add("client_join_game_method","joinGame");
        manager.add("client_start_game_method","startGame");
        manager.add("client_leave_game_method","leaveGame");
        manager.add("client_send_message_method","sendMessage");
        manager.add("client_set_train_deck_method","setTrainDeck");
        manager.add("client_set_dest_deck_method","setDestDeck");
        manager.add("client_add_game_action_method","addGameAction");
        manager.add("client_change_turns_method","changeTurns");
        manager.add("client_update_player_method","updatePlayer");
        manager.add("set_destination_option_cards","setDestOptionCards");
        manager.add("client_player_completed_setup","playerCompletedSetup");
        manager.add("client_claim_route_method","claimRoute");

        // Server Facade Methods
        manager.add("server_login_method","login");
        manager.add("server_register_method","register");
        manager.add("server_create_game_method","createGame");
        manager.add("server_join_game_method","joinGame");
        manager.add("server_start_game_method","startGame");
        manager.add("server_leave_game_method","leaveGame");
        manager.add("server_get_game_list_method","getActiveGames");
        manager.add("server_get_commands_method","getCommandList");
        manager.add("server_claim_route_method","claimRoute");
        manager.add("server_draw_face_up_method","drawFaceUp");
        manager.add("server_draw_face_down_method","drawFaceDown");
        manager.add("server_send_message_method","sendMessage");
        manager.add("server_draw_dest_cards_method","drawDestCards");
        manager.add("server_request_three_dest_cards","getThreeDestCards");
        manager.add("server_send_setup_results","sendSetupResults");
    }
}
