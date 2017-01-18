define('ProductivityTools/PageOperationsManager',
    [
        'jquery',
        'ajs'
    ],
    function ($, AJS) {

        var MENU_ITEM_SELECTOR = "#page-operations-item";
        var DIALOG_SELECTOR = "#page-operations-dialog";
        var dialog;

        var registerDialogOpen = function () {
            $(MENU_ITEM_SELECTOR).click(function (e) {
                e.preventDefault();
                dialog.show();
            });
        };

        var initDialog = function () {
            $("body").append(Confluence.Templates.ProductivityTools.operationsDialog());
            dialog = AJS.dialog2(DIALOG_SELECTOR);
            registerDialogOpen();
        };

        var init = function () {
            initDialog();
        };

        return {
            "init": init
        }

    }
);