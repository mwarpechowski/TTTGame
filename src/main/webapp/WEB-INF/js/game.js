// Game Board specific functions

$(document).ready(function () {
    initBoardTableEventHandlers();
    initGameControlsEventHandlers();
    updateGameStatus(gameId);
});

function initBoardTableEventHandlers() {
    $('tr').on('click', '.boardField:not(".X, .O, .disabled")', handleBoardFieldClicked);
    $('tr').on('mouseenter', '.boardField:not(".X, .O, .disabled")', handleBoardFieldMouseenter);
    $('tr').on('mouseleave', '.boardField:not(".X, .O, .disabled")', handleBoardFieldMouseleave);
}

function initGameControlsEventHandlers() {
    $('#hideAxesButton').on('click', toggleBoardAxes);
}

function updateGameStatus(gameId){
    $.ajax({
        method: 'GET',
        url: contextPath + '/api/game/' + gameId + '/status',
        success: function(msg) {
            markCurrentPlayer(msg.currentPlayer);
        }
    });
}

function markCurrentPlayer(symbol) {
    $('.playerName.current').removeClass('current');
    $('#player'+symbol).addClass('current');
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
    movePerformed(gameId, col, row);
}

function handleBoardFieldMouseenter(event) {
    $(this).addClass("hover");
}

function handleBoardFieldMouseleave(event) {
    $(this).removeClass("hover");
}

function toggleBoardAxes(event) {
    $('.boardVerticalAxis').toggleClass("hidden");
    $('.boardHorizontalAxis').toggleClass("hidden");
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
            function (move) {
                markMoveOnBoardField(move);
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
        success: [function(move){
            // remove last entry from moves list
            $('#move_'+move.seqNumber).remove();
        },
        function(move) {
            // update board (requires move coordinates)
            unmarkMoveOnBoardField(move);
        },
        function(move){
            // trigger game state check
            updateGameStatus(gameId);
        }]
    });
}

function markMoveOnBoardField(move) {
    var fieldId = '#boardField_'+move.col+'_'+move.row;
    $(fieldId).addClass(move.symbol);
    $(fieldId).removeClass('hover');  // after click mouse is still over field but new class (symbol) will prevent it from unmark
    $(fieldId).attr('data-moveid', move.seqNumber);
}

function unmarkMoveOnBoardField(move) {
    var fieldId = '#boardField_'+move.col+'_'+move.row;
    $(fieldId).removeClass(move.symbol);
    $(fieldId).removeAttr('data-moveid', null);
}

function markMoveListItem(moveId) {
    $("#move_"+moveId).addClass('hover');
}

function unmarkMoveListItem(moveId) {
    $("#move_"+moveId).removeClass('hover');
}
