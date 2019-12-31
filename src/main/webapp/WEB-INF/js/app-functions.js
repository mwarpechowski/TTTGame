function delete_game(gameId) {
    $.ajax({
        type: "DELETE",
        url: 'api/game/'+gameId,
        success: function(msg){
            $('#game_list_entry_'+gameId).remove();
        }
    });
}

function markMoveListItem(moveId) {
    $("#move_"+moveId).addClass('moveListItemHover');
}

function unmarkMoveListItem(moveId) {
    $("#move_"+moveId).removeClass('moveListItemHover');
}

function markBoardField(col, row) {
    $('#boardField_'+col+'_'+row).addClass('boardFieldHover');
}

function unmarkBoardField(col, row) {
    $('#boardField_'+col+'_'+row).removeClass('boardFieldHover');
}
