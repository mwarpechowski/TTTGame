// Game List specific functions

$(document).ready(function() {
    $("td").on('click', '.deleteButton', eventHandlerDeleteGame);
    $("select#gameMode").on('change', eventHandlerSelectGameMode);
    $("input#isFixedSizeBoard").on('change', eventHandlerIsFixedSizeBoard)
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

function eventHandlerSelectGameMode(event) {
    switch (event.currentTarget.value) {
        case "3":
            storeBoardSizeAndSet(3);
            $("#isFixedSizeBoard").prop("checked", true);
            $("#isFixedSizeBoard").prop("disabled", true);
            $("#boardSize").prop("disabled", false);
            $("#boardSize").prop("readonly", true);
            $("#boardSize").prop("min", 3);
            break;
        case "5":
            restoreBoardSize();
            $("#isFixedSizeBoard").prop("disabled", false);
            $("#boardSize").prop("readonly", false);
            $("#boardSize").prop("min", 5);
            break;
        default:
            console.log("Unexpected select value: " + event.currentTarget.value);
    }
}

function eventHandlerIsFixedSizeBoard(event) {
    if(event.currentTarget.checked){
        restoreBoardSize();
        $("#boardSize").prop("disabled", false);
    } else {
        storeBoardSizeAndSet(null);
        $("#boardSize").prop("disabled", true);
    }
}

function storeBoardSizeAndSet(newValue) {
    var input = $("#boardSize");
    var currentValue = input.val() || input.data("prevValue");
    input.data("prevValue", currentValue);
    input.val(newValue);
}

function restoreBoardSize() {
    var sizeValue = $("#boardSize").data("prevValue") || 15;
    $("#boardSize").val(sizeValue);
}