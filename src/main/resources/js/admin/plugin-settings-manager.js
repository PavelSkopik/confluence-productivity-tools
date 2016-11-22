define('ProductivityTools/PluginSettingsManager',
    [
        'jquery',
        'ajs'
    ],
    function ($,
              AJS) {

        var SETTINGS_RESOURCE = "/rest/productivity-tools/1.0/settings";
        var SETTINGS_FORM_SELECTOR = "#settings-form";
        var SAVE_BTN_SELECTOR = "#settings-section #btn-save";
        var SPINNER_SELECTOR = "#save-spinner";
        var ERROR_PANEL_SELECTOR = "#save-operation-error";
        var ERROR_TEXT_SELECTOR = "#save-operation-error-text";

        var errorPanel;

        var Settings = {
            maximumPageMergeCount: 0
        };

        var bindData = function (settingsForm, data) {
            for (var name in data) {
                var $element = settingsForm.find("#" + name);
                $element.val(data[name]);
            }
        };

        var load = function (settingsForm) {
            $.get(AJS.params.contextPath + SETTINGS_RESOURCE)
                .success(function (data) {
                    bindData(settingsForm, data);
                }).error(function (jqXHR, textStatus, errorThrown) {
                    console.log("Could not load export configuration. The following error occurred: " + textStatus + " - " + errorThrown);

                    errorPanel.removeClass("hidden");
                    errorPanel.find(ERROR_TEXT_SELECTOR).text("Could not load export configuration. The following error occurred: " + textStatus + " - " + errorThrown + ". View the Javascript console for details.");
                });
        };

        var getSettings = function (settingsForm) {
            return {
                "maximumPageMergeCount": settingsForm.find("#maximumPageMergeCount").val()
            }
        };

        var save = function (settings) {
            var spinner = $(SPINNER_SELECTOR);
            spinner.spin();

            $.ajax({
                type:        "POST",
                url:         AJS.params.baseUrl + SETTINGS_RESOURCE,
                contentType: 'application/json',
                data:        JSON.stringify(settings)
            }).success(function () {
                spinner.spinStop();
            }).error(function (jqXHR, textStatus, errorThrown) {
                spinner.spinStop();

                console.log("Could not save export configuration. The following error occurred: " + textStatus + " - " + errorThrown);
                spinner.spinStop();

                errorPanel.removeClass("hidden");
                errorPanel.find(ERROR_TEXT_SELECTOR).text("Could not save export configuration. The following error occurred: " + textStatus + " - " + errorThrown + ". View the Javascript console for details.");
            });
        };

        var init = function () {
            var settingsForm = $(SETTINGS_FORM_SELECTOR);
            errorPanel = $(ERROR_PANEL_SELECTOR);

            load(settingsForm);

            $(SAVE_BTN_SELECTOR).on("click", function () {
                save(getSettings(settingsForm));
            });

        };

        return {
            init: init
        }
    }
);
