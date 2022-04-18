"use strict";
$(function() {
    var n, a, e = $(".datatables-basic"),
        r = (e.length && (n = e.DataTable({
            order: [
                [1, "desc"]
            ],
            dom: '<"card-header flex-column flex-md-row"<"head-label text-center"><"dt-action-buttons text-end pt-3 pt-md-0"B>><"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6 d-flex justify-content-center justify-content-md-end"f>>t<"row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>>',
            displayLength: 5,
            lengthMenu: [5, 10, 25, 50, 75, 100],
            buttons: [{
                extend: "collection",
                className: "btn btn-label-primary dropdown-toggle me-2",
                text: '<i class="bx bx-export me-sm-2"></i> <span class="d-none d-sm-inline-block">Export</span>',
                buttons: [{
                    extend: "print",
                    text: '<i class="bx bx-printer me-2" ></i>Print',
                    className: "dropdown-item",
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5]
                    }
                }, {
                    extend: "csv",
                    text: '<i class="bx bx-file me-2" ></i>Csv',
                    className: "dropdown-item",
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5]
                    }
                }, {
                    extend: "excel",
                    text: "Excel",
                    className: "dropdown-item",
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5]
                    }
                }, {
                    extend: "pdf",
                    text: '<i class="bx bxs-file-pdf me-2"></i>Pdf',
                    className: "dropdown-item",
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5]
                    }
                }, {
                    extend: "copy",
                    text: '<i class="bx bx-copy me-2" ></i>Copy',
                    className: "dropdown-item",
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5]
                    }
                }]
            }],
            responsive: {
                details: {
                    display: $.fn.dataTable.Responsive.display.modal({
                        header: function(e) {
                            return "Details of User"
                        }
                    }),
                    type: "column",
                    renderer: function(e, a, t) {
                        t = $.map(t, function(e, a) {
                            return "" !== e.title ? '<tr data-dt-row="' + e.rowIndex + '" data-dt-column="' + e.columnIndex + '"><td>' + e.title + ":</td> <td>" + e.data + "</td></tr>" : ""
                        }).join("");
                        return !!t && $('<table class="table"/><tbody />').append(t)
                    }
                }
            }
        }), $("div.head-label").html('<h5 class="card-title mb-0">Users</h5>')), 101);

});