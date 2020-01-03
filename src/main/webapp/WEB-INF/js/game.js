// Game Board specific functions

$(document).ready(initBoardTableEventHandlers);

function initBoardTableEventHandlers() {
    $('tr').on('click', '.boardField:not(".X, .O, .disabled")', handleBoardFieldClicked);
    $('tr').on('mouseenter', '.boardField:not(".X, .O, .disabled")', handleBoardFieldMouseenter);
    $('tr').on('mouseleave', '.boardField:not(".X, .O, .disabled")', handleBoardFieldMouseleave);
}

function enableBoardTable() {
    $('.boardField').removeClass("disabled");
}

function disableBoardTable() {
    $('.boardField').addClass("disabled");
}

function handleBoardFieldClicked(event) {
    var col = $(this).data('col');
    var row = $(this).data('row');
    var gameId = $('#gameBoardTable').data('gameid');
    movePerformed(gameId, col, row);
}

function handleBoardFieldMouseenter(event) {
    $(this).addClass("hover");
}

function handleBoardFieldMouseleave(event) {
    $(this).removeClass("hover");
}



function markMoveListItem(moveId) {
    $("#move_"+moveId).addClass('hover');
}

function unmarkMoveListItem(moveId) {
    $("#move_"+moveId).removeClass('hover');
}

function markBoardField(col, row) {
    $('#boardField_'+col+'_'+row).addClass('hover');
}

function unmarkBoardField(col, row) {
    $('#boardField_'+col+'_'+row).removeClass('hover');
}

function movePerformed(gameId, col, row) {
    var moveRequest = JSON.parse('{"col":' + col + ',"row":' + row + ',"gameId":' + gameId +'}');
    $.ajax({
        method: "PUT",
        url: contextPath + '/api/game/' + gameId + '/move',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(moveRequest),
        processData: false,
        success: [
            function (msg) {
                // mark boardFiled
                var fieldId = '#boardField_'+msg.col+'_'+msg.row;
                //$(fieldId).append(msg.symbol);
                $(fieldId).addClass(msg.symbol);
                $(fieldId).removeClass('hover');  // after click mouse is still over field but new class (symbol) will prevent it from unmark
                $(fieldId).data('moveid', msg.id);
            },
            function(msg){
                // add entry to moves list
                var moveElem = document.createElement("p");
                moveElem.id = 'move_'+msg.seqNumber;
                moveElem.classList.add('move_'+msg.symbol);
                var moveElemSpan = document.createElement("span");
                moveElemSpan.append(document.createTextNode(msg.symbol + ': col=' + msg.col + ' row=' + msg.row));
                moveElem.append(moveElemSpan);
                $("#leftSidebarBox").append(moveElem);
                // add event listeners when new element is already inserted into document
            },
            function(msg) {
                var moveId = '#move_'+msg.seqNumber;
                $(moveId).on('mouseenter', function() {markMoveListItem(msg.seqNumber)});
                $(moveId).on('mouseenter', function() {markBoardField(msg.col, msg.row)});
                $(moveId).on('mouseleave', function() {unmarkMoveListItem(msg.seqNumber)});
                $(moveId).on('mouseleave', function() {unmarkBoardField(msg.col, msg.row)});
            },
            function(msg){
                // trigger game state check
                updateGameStatus(gameId);
            }]
    });
}

function undoLastMove() {
    $.ajax({
        type: "DELETE",
        url: contextPath + '/api/game/' + gameId + '/move',
        success: [function(msg){
            // remove last entry from moves list
        },
        function(msg) {
            // update board (requires move coordinates)
        },
        function(msg){
            // trigger game state check
            updateGameStatus(gameId);
        }]
    });
}

function updateGameStatus(gameId){

}
