<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>定价资源列表--${site.name}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel = "shortcut icon" href="${site.logo}">
    <link rel="stylesheet" href="${base}/static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${base}/static/css/user.css" media="all" />
</head>
<body class="childrenBody">
<fieldset class="layui-elem-field">
    <legend>资源检索</legend>
    <div class="layui-field-box">
    <form class="layui-form">
        <div class="layui-inline" style="margin-left: 15px">
            <label>文件名称:</label>
            <div class="layui-input-inline">
                <input type="text" value="" name="remarks" placeholder="请输入文件名" class="layui-input search_input">
            </div>
        </div>
        <div class="layui-inline">
            <a class="layui-btn" lay-submit="" lay-filter="searchForm">查询</a>
        </div>
        <div class="layui-inline" >
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-warm" href="http://q8iig6y6e.bkt.clouddn.com/sxxcck/%E5%AE%9A%E4%BB%B7%E6%9F%A5%E8%AF%A2%E6%A8%A1%E6%9D%BF.xlsx">模板下载</a>
        </div>
        <div class="layui-inline" id="content">
            <button type="button" class="layui-btn layui-btn-normal" id="test2"><i class="layui-icon"></i>上传定价文件</button>
        </div>
    </form>
    </div>
</fieldset>
<div class="layui-form users_list">
    <table class="layui-table" id="test" lay-filter="demo"></table>

    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
</div>
<div id="page"></div>
<div id='jsonContainer' class="Canvas" style="display: none"></div>
<script>
    var baseDir = '${base}';
</script>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/tools.js"></script>
<script>
    layui.use(['layer','form','table','upload'], function() {
        var layer = layui.layer,
                $ = layui.jquery,
                form = layui.form,
                table = layui.table,
                upload=layui.upload,
                t;                  //表格数据变量

        t = {
            elem: '#test',
            url:'${base}/admin/purchase/price/resource/list',
            method:'post',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                groups: 5, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
                limits:[3,10, 20, 30]
            },
            width: $(parent.window).width()-223,
            cols: [[
                {type:'checkbox'},
                {field:'id', title: 'ID', width:'10%'},
                {field:'remarks', title: '文件名称'},
                {field:'fileSize',    title: '文件大小', width:'15%'},
                {field:'fileType',    title: '文件类型', width:'15%'},
                {field:'createDate',  title: '创建时间', width:'15%',templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
                {fixed: 'right', title: '操作', width: '15%',align: 'center',toolbar: '#barDemo'}
            ]]
        };
        table.render(t);

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === "del"){
                layer.confirm("你确定要删除该资源么？",{btn:['是的,我确定','我再想想']},
                    function(){
                        $.post("${base}/admin/purchase/price/resource/delete",{"sid":data.id},function (res){
                           if(res.success){
                               layer.msg("删除成功",{time: 1000},function(){
                                   table.reload('test', t);
                               });
                           }else{
                               layer.msg(res.message);
                           }
                        });
                    }
                )
            }
        });
        //指定允许上传的文件类型
        upload.render({
            elem: '#test2'
            ,field: 'test'
            ,url: '${base}/file/upload' //改成您自己的上传接口
            ,accept: 'file' //普通文件
            ,done: function(res){
                if (res.success == true){
                    console.log(res.data);
                    layer.msg('上传成功');
                    table.render(t);        // 刷新表格
                    $.get('${base}/admin/purchase/price/resource/parse', {path: res.data.url});
                }
            }
        });

        //搜索
        form.on("submit(searchForm)",function(data){
            t.where = data.field;
            table.reload('test', t);
            return false;
        });

    });
</script>
</body>
</html>