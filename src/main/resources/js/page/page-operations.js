require([
        'ajs',
        'ProductivityTools/PageOperationsManager'
    ],
    function (AJS, PageOperationsManager) {
        AJS.toInit(function () {
            PageOperationsManager.init();
        });
    }
);