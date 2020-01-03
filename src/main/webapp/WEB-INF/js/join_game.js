// Game List specific functions

$(document).ready(function() {
    $("td").on('click', '.deleteButton', eventHandlerDeleteGame)
});

function eventHandlerDeleteGame(event) {
    var gameId = $(this).data('gameid');
    $.ajax({
            type: "DELETE",
            url: contextPath + '/api/game/' + gameId,
            success: function(msg){
                $('#game_list_entry_'+gameId).remove();
            }
        });
}