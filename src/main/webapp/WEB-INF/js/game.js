// Game Board specific functions

$(document).ready(function () {
    initBoardTableEventHandlers();
    initGameControlsEventHandlers();
    initMovesHistoryEventHandlers();
    initBoardResizeSlider();
    updateGameStatus();
});

function initBoardTableEventHandlers() {
    $('tr').on('click', '.boardField:not(".X, .O, .disabled")', handleBoardFieldClicked);
    $('tr').on('mouseenter', '.boardField:not(".X, .O, .disabled")', handleBoardFieldMouseenter);
    $('tr').on('mouseleave', '.boardField:not(".X, .O, .disabled")', handleBoardFieldMouseleave);
}

function initGameControlsEventHandlers() {
    $('#hideAxesButton').on('click', toggleBoardAxes);
    $('#showMovesHistoryButton').on('click', toggleMovesHistory);
}

function initMovesHistoryEventHandlers() {
    $("#rightSidebarBox").on('mouseenter', '#movesHistory li', handleMovesHistoryItemMouseenter);
    $("#rightSidebarBox").on('mouseleave', '#movesHistory li', handleMovesHistoryItemMouseleave);
}

function initBoardResizeSlider() {
    $("#gameBoardResizeSlider").on('input change', handleBoardResize);
    $("#gameBoardResizeSlider").attr("min", parseInt($('.boardField').css("min-width"),10) * 100);
    $("#gameBoardResizeSlider").attr("max", parseInt($('.boardField').css("max-width"),10) * 100);
    $("#gameBoardResizeSlider").attr("value", parseInt($('.boardField').css("width"),10) * 100);
}

function updateGameStatus(){
    $.ajax({
        method: 'GET',
        url: contextPath + '/api/game/' + gameId + '/status',
        success: function(msg) {
            markCurrentPlayer(msg.currentPlayer);
            markWinner(msg.winner);
            lockBoardTable(msg.finished);
        }
    });
}

function markCurrentPlayer(symbol) {
    $('.playerName.current').removeClass('current');
    $('#player'+symbol).addClass('current');
}

function markWinner(symbol) {
    $('.winner').removeClass("winner");
    $('#player'+symbol).addClass("winner");
}

function lockBoardTable(doLock) {
    if(doLock){
        disableBoardTable();
    } else {
        enableBoardTable();
    }
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

function handleMovesHistoryItemMouseenter(event) {
    $(this).addClass("hover");
    var boardFieldId = '#boardField_' + $(this).attr('data-col') + '_' + $(this).attr('data-row');
    $(boardFieldId).addClass("hover");
}

function handleMovesHistoryItemMouseleave(event) {
    $(this).removeClass("hover");
    var boardFieldId = '#boardField_' + $(this).attr('data-col') + '_' + $(this).attr('data-row');
    $(boardFieldId).removeClass("hover");
}

function handleBoardResize(event) {
    var newValue = event.target.value;
    var minValue = event.target.getAttribute("min");
    var maxValue = event.target.getAttribute("max");
    var cssMax = parseFloat($(".boardField").css("max-width"), 10);
    var newSize = cssMax * newValue / maxValue;
    var displayedSize = Math.round(newValue / minValue *100) + '%';
    $(".boardField").css("width", newSize);
    $(".boardHorizontalAxis:not(.boardVerticalAxis)").css("width", newSize);
    $(".boardField").css("padding-bottom", "calc(" + newSize + "px - 1em)");
    $("#gameBoardSizeValue").html(displayedSize);
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
            markMoveOnBoardField,
            addEntryToMovesHistory,
            updateGameStatus
        ]
    });
}

function undoLastMove() {
    $.ajax({
        type: "DELETE",
        url: contextPath + '/api/game/' + gameId + '/move',
        success: [
            removeEntryFromMovesHistory,
            unmarkMoveOnBoardField,
            updateGameStatus
        ]
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

function addEntryToMovesHistory(move) {
    var movesHistoryEntry = createMoveHistoryEntry(move);
    $('#movesHistory').append(movesHistoryEntry);
}

function createMoveHistoryEntry(move) {
    var symbol = document.createElement("span");
    symbol.classList.add("symbol");
    symbol.append(document.createTextNode(move.symbol + ':'));

    var column = document.createElement("span");
    column.classList.add("column");
    column.append(document.createTextNode('col=' + move.col));

    var row = document.createElement("span");
    row.classList.add("row");
    row.append(document.createTextNode('row=' + move.row));

    var historyEntry = document.createElement("li");
    historyEntry.id = 'move_'+move.seqNumber;
    historyEntry.setAttribute("data-moveid", move.seqNumber);
    historyEntry.setAttribute("data-col", move.col);
    historyEntry.setAttribute("data-row", move.row);
    historyEntry.append(symbol);
    historyEntry.append(column);
    historyEntry.append(row);

    return historyEntry;
}

function removeEntryFromMovesHistory(move) {
    $('#move_'+move.seqNumber).remove();
}

function toggleMovesHistory(event) {
    if ($("#movesHistoryBox").length) {
        $("#movesHistoryBox").remove();
    } else {
        fetchMovesHistory();
    }
}

function fetchMovesHistory() {
    $.ajax({
        type: "GET",
        url: contextPath + '/game/' + gameId + '/move',
        success: function (msg) {
            $('#rightSidebarBox').append(msg);
        }
    });
}
