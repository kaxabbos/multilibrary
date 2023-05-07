$(function() {
    $("#slider_range").slider({
        range: true,
        min: 1900,
        max: 2100,
        values: [1900, 2100],
        slide: function(event, ui) { $("#date_range").val(ui.values[0] + " - " + ui.values[1]); }
    });
    $("#date_range").val($("#slider_range").slider("values", 0) +
        " - " + $("#slider_range").slider("values", 1));
});