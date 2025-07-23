$(document).ready(function() {
    // API地址（请根据实际情况修改）
    var api1 = 'http://localhost:8089/api/sc/student/list';
    var api2 = 'http://localhost:8089/api/sc/course/list';
    var queryApi = 'http://localhost:8089/api/sc/custom';

    var pageSize = 5; // 每页条数，和后端size一致
    var sortField = 'id';
    var sortOrder = 'desc';
    var state = {
        table1: { page: 1, pages: 1 },
        table2: { page: 1, pages: 1 }
    };

    // 操作符列表
    var operators = [
        { value: '=', text: '=' },
        { value: '!=', text: '!=' },
        { value: '>', text: '>' },
        { value: '<', text: '<' },
        { value: '>=', text: '>=' },
        { value: '<=', text: '<=' }
    ];
    var logicTypes = [
        { value: 'and', text: 'AND' },
        { value: 'or', text: 'OR' }
    ];

    // 加载表格数据
    function loadTable(api, tableId, builderId, page) {
        page = page || 1;
        $.get(api, { page: page, size: pageSize, sort: sortField, order: sortOrder }, function(res) {
            renderTable(res.data.list, tableId);
            renderPagination(tableId, res.data.pages, page);
            renderQueryBuilder(res.data.list, builderId, tableId);
            state[tableId].page = page;
            state[tableId].pages = res.data.pages;
        });
    }

    // 渲染表格
    function renderTable(list, tableId) {
        if (!list || list.length === 0) {
            $('#' + tableId).html('<tr><td>暂无数据</td></tr>');
            return;
        }
        var columns = list[0].map(function(item) { return item.fieldCnName; });
        var fieldNames = list[0].map(function(item) { return item.fieldName; });
        var thead = '<thead><tr>' + columns.map(function(c) { return '<th>' + c + '</th>'; }).join('') + '</tr></thead>';
        var tbody = '<tbody>' + list.map(function(row) {
            return '<tr>' + row.map(function(cell) {
                return '<td>' + (cell.fieldValue ? cell.fieldValue.join(',') : '') + '</td>';
            }).join('') + '</tr>';
        }).join('') + '</tbody>';
        $('#' + tableId).html(thead + tbody);
        // 存储列信息到table元素上，供查询构建器用
        $('#' + tableId).data('columns', list[0]);
    }

    // 渲染分页栏
    function renderPagination(tableId, pages, currentPage) {
        var $section = $('#' + tableId + '-section');
        $section.find('.pagination').remove();
        if (!pages || pages <= 1) return;
        var $pagination = $('<div class="pagination"></div>');
        var prev = $('<button type="button">上一页</button>');
        prev.prop('disabled', currentPage === 1);
        prev.on('click', function() {
            if (currentPage > 1) {
                loadTable(
                    tableId === 'table1' ? api1 : api2,
                    tableId,
                    tableId === 'table1' ? 'query-builder1' : 'query-builder2',
                    currentPage - 1
                );
            }
        });
        $pagination.append(prev);
        for (var i = 1; i <= pages; i++) {
            var btn = $('<button type="button"></button>').text(i);
            if (i === currentPage) btn.addClass('active');
            (function(page) {
                btn.on('click', function() {
                    loadTable(
                        tableId === 'table1' ? api1 : api2,
                        tableId,
                        tableId === 'table1' ? 'query-builder1' : 'query-builder2',
                        page
                    );
                });
            })(i);
            $pagination.append(btn);
        }
        var next = $('<button type="button">下一页</button>');
        next.prop('disabled', currentPage === pages);
        next.on('click', function() {
            if (currentPage < pages) {
                loadTable(
                    tableId === 'table1' ? api1 : api2,
                    tableId,
                    tableId === 'table1' ? 'query-builder1' : 'query-builder2',
                    currentPage + 1
                );
            }
        });
        $pagination.append(next);
        $section.append($pagination);
    }

    // 渲染查询构建器（支持多个条件组，每组可增删条件）
    function renderQueryBuilder(list, builderId, tableId) {
        var columns = list[0];
        var $builder = $('#' + builderId);
        $builder.empty();
        // 初始添加一个组
        addConditionGroup($builder, columns);
        // 添加组按钮
        var addGroupBtn = $('<button type="button">添加条件组(AND/OR)</button>');
        addGroupBtn.on('click', function() {
            addConditionGroup($builder, columns);
        });
        $builder.append(addGroupBtn);
    }

    // 添加一个条件组
    function addConditionGroup($builder, columns) {
        var $group = $('<div class="condition-group"></div>');
        var logicSelect = $('<select class="logic-type-group"></select>');
        logicTypes.forEach(function(op) {
            logicSelect.append('<option value="' + op.value + '">' + op.text + '</option>');
        });
        $group.append(logicSelect);
        var $itemsWrap = $('<div class="group-items"></div>');
        $group.append($itemsWrap);
        addConditionItem($itemsWrap, columns);
        // 添加条件按钮
        var addItemBtn = $('<button type="button">+</button>');
        addItemBtn.on('click', function() {
            addConditionItem($itemsWrap, columns);
        });
        $group.append(addItemBtn);
        // 删除组按钮
        var delGroupBtn = $('<button type="button">删除组</button>');
        delGroupBtn.on('click', function() { $group.remove(); });
        $group.append(delGroupBtn);
        $builder.append($group);
    }

    // 添加组内单个条件
    function addConditionItem($itemsWrap, columns) {
        var $row = $('<div class="query-row"></div>');
        var colSelect = $('<select class="col-name"></select>');
        columns.forEach(function(col) {
            colSelect.append('<option value="' + col.fieldName + '" data-table="' + col.table + '">' + col.fieldCnName + '</option>');
        });
        var opSelect = $('<select class="operator"></select>');
        operators.forEach(function(op) {
            opSelect.append('<option value="' + op.value + '">' + op.text + '</option>');
        });
        var valueInput = $('<input type="text" class="value" placeholder="输入取值" />');
        var delBtn = $('<button type="button">-</button>');
        delBtn.on('click', function() { $row.remove(); });
        $row.append(colSelect, opSelect, valueInput, delBtn);
        $itemsWrap.append($row);
    }

    // 查询按钮事件
    $('#query-btn1').off('click').on('click', function() {
        handleQuery('table1', 'query-builder1');
    });
    $('#query-btn2').off('click').on('click', function() {
        handleQuery('table2', 'query-builder2');
    });

    // 处理查询（多条件组）
    function handleQuery(tableId, builderId) {
        var $builder = $('#' + builderId);
        var $groups = $builder.find('.condition-group');
        var columns = $('#' + tableId).data('columns');
        var conditions = [];
        $groups.each(function() {
            var $group = $(this);
            var logic = $group.find('.logic-type-group').val();
            var $rows = $group.find('.query-row');
            var items = [];
            $rows.each(function() {
                var $row = $(this);
                var fieldName = $row.find('.col-name').val();
                var operator = $row.find('.operator').val();
                var value = $row.find('.value').val();
                var col = columns.find(function(c) { return c.fieldName === fieldName; });
                if (col && value) {
                    items.push({
                        table: col.table,
                        fieldName: fieldName,
                        operator: operator,
                        fieldValue: [value]
                    });
                }
            });
            if (items.length > 0) {
                conditions.push({
                    conditionType: logic,
                    conditionItems: items
                });
            }
        });
        // 构造API参数
        var postData = {
            conditions: conditions,
            page: state[tableId].page || 1,
            size: pageSize,
            sort: sortField,
            order: sortOrder
        };
        $.ajax({
            url: queryApi,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(postData),
            success: function(res) {
                renderTable(res.data.list, tableId);
                renderPagination(tableId, res.data.pages, postData.page);
            },
            error: function() {
                alert('查询失败');
            }
        });
    }

    // 页面初始化
    loadTable(api1, 'table1', 'query-builder1', 1);
    loadTable(api2, 'table2', 'query-builder2', 1);
}); 