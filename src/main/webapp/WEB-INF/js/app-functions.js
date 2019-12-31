function delete_game(gameId) {
    $.ajax({
        type: "DELETE",
        url: 'api/game/'+gameId,
        success: function(msg){
            $('#game_list_entry_'+gameId).remove();
        }
    });
}