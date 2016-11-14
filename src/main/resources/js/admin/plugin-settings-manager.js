define('ProductivityTools/PluginSettingsManager',
    [
        'jquery',
        'ajs'
    ],
    function ($,
              AJS) {

        var SETTINGS_RESOURCE = "/rest/productivity-tools/1.0/settings";
        var SETTINGS_FORM_SELECTOR = "";
        var SAVE_BTN_SELECTOR = "";

        var Settings = {
            maximumPageMergeCount: 0
        };

        var bindData = function (data) {

        };

        var load = function () {
            $.get(AJS.params.contextPath + SETTINGS_RESOURCE)
                .success(function (data) {
                    bindData(data);
                }).error(function () {

                });
        };

        var getSettings = function () {

        };

        var save = function (settingsForm) {
            $.ajax({
                type:        "POST",
                url:         AJS.params.baseUrl + SETTINGS_RESOURCE,
                contentType: 'application/json',
                data:        JSON.stringify(settings)
            }).success(function (data) {
                console.log(data);
            }).error(function (jqXHR, textStatus, errorThrown) {

            });
        };

        var init = function () {
            var settingsForm = $(SETTINGS_FORM_SELECTOR);

            load(settingsForm);

            $(SAVE_BTN_SELECTOR).on("click", function () {
                save(settingsForm);
            });

        };

        return {
            init: init
        }
    }
);
