"use strict";
! function() {
    var validationPages = [];
    const e = $(".select2"),
        a = $(".selectpicker"),
        i = document.querySelector("#wizard-validation");
    if (i, null !== i) {
        var options = {
            framework: 'bootstrap',
            fields: {
                inputAnswer: {
                    validators: {
                        notEmpty: {
                            message: "An answer should be given"
                        },
                        stringLength: {
                            min: 3,
                            max: 250,
                            message: "The name must be more than 3 and less than 250 characters long"
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9 ]+$/,
                            message: "The name can only consist of alphabetical, number and space"
                        }
                    }
                }
            },
            plugins: {
                trigger: new FormValidation.plugins.Trigger,
                bootstrap5: new FormValidation.plugins.Bootstrap5({
                    eleValidClass: "",
                    rowSelector: function(e, a) {
                        switch (e) {
                            case "inputAnswer":
                                return ".answerSelector";
                            default:
                                return ".row"
                        }
                    }
                }),
                autoFocus: new FormValidation.plugins.AutoFocus,
                submitButton: new FormValidation.plugins.SubmitButton
            },
            init: e => {
                e.on("plugins.message.placed", function(e) {
                    e.element.parentElement.classList.contains("input-group") && e.element.parentElement.insertAdjacentElement("afterend", e.messageElement)
                })
            }
        };
        var radioButtonAnswers = document.querySelectorAll('div[name^="groupDiv"]');
        if (radioButtonAnswers) {
            radioButtonAnswers.forEach(function(value, index) {
                options.fields['group' + (index + 1)] = {
                    validators: {
                        notEmpty: {
                            message: "An answer should be given"
                        }
                    }
                };
            })
        }
        const t = i.querySelector("#wizard-form"),
            o = t.querySelectorAll('div[name="eachQuestion"]');
        const n = [].slice.call(t.querySelectorAll(".btn-next")),
            r = [].slice.call(t.querySelectorAll(".btn-prev")),
            s = new Stepper(i, {
                linear: !0
            });

        o.forEach(function(value, index) {
            if(o.length != index+1){
                validationPages.push(
                    FormValidation.formValidation(value, options)
                    .on("core.form.valid", function() {
                        s.next()
                    }));
            }
            else{
                validationPages.push(
                    FormValidation.formValidation(value, options)
                    .on("core.form.valid", function() {
                        var surveyId = $('[name="survey"]').attr('id');
                        var surveyResults = [];
                        $('[name="eachQuestion"]').each(function(questionIndex,qElement){
                            var surveyResult = {surveyId:0,questionId:0,answerId:0,answerText:""};
                            surveyResult.surveyId = surveyId;
                            surveyResult.questionId = $(qElement).find("[name='questionId']").attr('id');
                            var selectedRadio = $(this).find('input[name^="group"]:checked');
                            if(selectedRadio[0])
                                surveyResult.answerId = selectedRadio.val();
                            else{
                                surveyResult.answerId = $(this).find("[name='answerId']").attr('id');
                                surveyResult.answerText = $(this).find('#inputAnswer').val();
                            }
                            surveyResults.push(surveyResult);
                        });
                        $.ajax({
                          url: "/surveys/complete",
                          type: "POST",
                          contentType: "application/json",
                          data: JSON.stringify(surveyResults),
                          success: function(response) {
                               $('#resultMessage').addClass("alert alert-success").html("Survey is completed successfully");
                               $('#btnModalOK').attr("href", "/surveys");
                               $('#modal').modal('show');
                          },
                          error: function(jqXHR, exception) {
                                var errorDetail = "";
                                if(jqXHR.status === 400){
                                    $(jqXHR.responseJSON.subErrors).each(function(index,element){
                                        errorDetail +=element.message;
                                    });
                                }
                                else{
                                    errorDetail = exception.message;
                                }
                                $('#resultMessage').addClass("alert alert-danger").html("Survey is not completed: Cause "+errorDetail);
                                $('#btnModalOK').attr("href", "#").attr("data-bs-dismiss","modal");
                                $('#modal').modal('show');
                          },
                        });
                    }));
            }
        });

        n.forEach(e => {
            e.addEventListener("click", e => {
                validationPages[s._currentIndex].validate().then(function(e){console.log(e)})
            })
        });
        r.forEach(e => {
            e.addEventListener("click", e => {
                if (s._currentIndex != 0) {
                    s.previous()
                }
            })
        });
    }
}();