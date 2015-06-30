//汽车发动机
var Qiche1=[
              {part: '发动机', version: '发动机1',date:'2014-10-1',other:'',versioninfo:'张三三于2014年11月1日创建'},
              {part: '车轮', version: '车轮2',date:'2014-10-2',other:'',versioninfo:'张三三于2014年11月1日创建'},
              {part: '车身', version: '车身3',date:'2014-10-3',other:'',versioninfo:'张三三于2014年11月1日创建'}
              ];
//发动机原材料
var Yuancailiao=[
              {input: '铁', number1: '100',output:'CO2',number2:'100',from:'杭汽轮',quality:'100%'},
              {input: '铁', number1: '100',output:'CO2',number2:'100',from:'杭汽轮',quality:'100%'}
              ];
//性别
var Genders = [
    {id: 1, name: '男'},
    {id: 2, name: '女'}
];
//国家
var Countrys = [
    {id: 'QT', name: '其他'},
    {id: 'CN', name: '中国'},
    {id: 'USA', name: '美国'},
    {id: 'EN', name: '英国'},
    {id: 'JP', name: '日本'}    
]
//部门
var Departments = [
    {name: '财务部', id: 'CW'},
    {name: '市场部', id: 'SC'},
    {name: '技术部', id: 'JS'}
];
var Positions = [
    {name: '财务部', id: 'CW', enableSelect: false, icon: 'e-tree-folder',
        children: [
            {name: '财务总监', id: 'CW1'},
            {name: '会计', id: 'CW2'},
            {name: '出纳', id: 'CW3'}
        ]
    },
    {name: '市场部', id: 'SC',enableSelect: false, icon: 'e-tree-folder',
        children: [
            {name: '市场总监', id: 'SC1'},
            {name: '销售经理', id: 'SC2'},
            {name: '业务员', id: 'SC3'}
        ]
    },
    {name: '技术部', id: 'JS',enableSelect: false, icon: 'e-tree-folder',
        children: [
            {name: '项目经理', id: 'JS1'},
            {name: '后台工程师', id: 'JS2'},
            {name: '前端工程师', id: 'JS3'},
            {name: '美工', id: 'JS4'},
            {name: '实习生', id: 'JS5'}
        ]
    }
];
//兴趣爱好
var Interests = [
    {id: 1, name: '游戏'},
    {id: 2, name: '小说'},
    {id: 3, name: '足球'},
    {id: 4, name: '旅游'},
    {id: 5, name: '摄影'},
    {id: 6, name: '蹦极'},
    {id: 7, name: '音乐'}
];
//婚否
var MarryStatus = [    
    {id: 1, name: '已婚'},
    {id: 2, name: '未婚'}
];
//学历
var Educationals = [
    {id: 1, name: '其他'},
    {id: 2, name: '大专'},
    {id: 3, name: '本科'},
    {id: 4, name: '研究生'},
    {id: 5, name: '博士'}
];
/*       
    员工账号:   ID              唯一的英文名        
    部门:       department      树形结构, 演示TreeSelect
    职位:       position        文本    
    姓名:       name    
    性别:       gender          2个选项,单选,radio
    婚否:       married         一个选项,checkbox
    出生日期:   birthday        Date日期选择器
    年龄:       age             spinner
    薪水:       salary          数字(掩码)
    学历:       educational     多选项单选, combo
    邮箱:       mail            邮箱验证    
    国家:       country         拉下选择
    兴趣:       interest        多选, checkgroup
    备注:       notes           textarea
*/
var Employees = [
    {ID: 'zhangsan', department: 'CW', position: 'CW1', name: '张三', gender: 1, married: 1, birthday: '1988-05-20', salary: 11000, educational: 4, mail: '001@edojs.com', age: 21, country: 'CN', interest: '1,3,5', notes: ''},
    {ID: 'lisi', department: 'JS', position: 'JS3', name: '李四', gender: 2, married: 2, birthday: '1985-05-20', salary: 3000, educational: 2, mail: '002@edojs.com', age: 28, country: 'EN', interest: '3,5', notes: ''},
    {ID: 'wangwu', department: 'SC', position: 'SC1', name: '王五', gender: 2, married: 2, birthday: '1998-05-20', salary: 7500, educational: 1, mail: '003@edojs.com', age: 33, country: 'QT', interest: '2,5', notes: ''},
    {ID: 'zhangliu', department: 'CW', position: 'CW2', name: '赵六', gender: 1, married: 1, birthday: '1988-05-20', salary: 100000, educational: 5, mail: '004@edojs.com', age: 55, country: 'JP', interest: '1,2,3', notes: ''},
    {ID: 'jack', department: 'JS', position: 'JS5', name: '杰克', gender: 2, married: 1, birthday: '1988-05-20', salary: 12000, educational: 3, mail: '005@edojs.com', age: 12, country: 'USA', interest: '3,5', notes: ''},
    {ID: 'niko', department: 'SC', position: 'SC1', name: '尼克', gender: 2, married: 2, birthday: '2018-05-20', salary: 70000, educational: 5, mail: '006@edojs.com', age: 56, country: 'CN', interest: '5', notes: ''},
    {ID: 'lingda', department: 'JS', position: 'JS2', name: '琳达', gender: 2, married: 1, birthday: '1988-05-20', salary: 60000, educational: 4, mail: '007@edojs.com', age: 32, country: 'EN', interest: '6,7', notes: ''},
    {ID: 'jetty', department: 'JS', position: 'JS2', name: '杰瑞', gender: 1, married: 1, birthday: '1988-05-20', salary: 10000, educational: 4, mail: '008@edojs.com', age: 24, country: 'USA', interest: '2,4', notes: ''},
    {ID: 'tom', department: 'CW', position: 'CW1', name: '汤姆', gender: 2, married: 1, birthday: '1988-05-20', salary: 7000, educational: 4, mail: '009@edojs.com', age: 28, country: 'JP', interest: '3', notes: ''},
    {ID: 'cat', department: 'JS', position: 'JS4', name: '凯特', gender: 2, married: 2, birthday: '1988-05-20', salary: 15000, educational: 5, mail: '0010@edojs.com', age: 18, country: 'CN', interest: '1,2', notes: ''},
    {ID: 'mark', department: 'SC', position: 'SC2', name: '马克', gender: 1, married: 1, birthday: '1988-05-20', salary: 4000, educational: 4, mail: '0011@edojs.com', age: 21, country: 'USA', interest: '3,4', notes: ''},
    {ID: 'andy', department: 'CW', position: 'CW3', name: '安迪', gender: 2, married: 1, birthday: '1988-05-20', salary: 8500, educational: 3, mail: '0012@edojs.com', age: 27, country: 'JP', interest: '3,6', notes: ''},
    {ID: 'merry', department: 'SC', position: 'SC3', name: '玛丽', gender: 1, married: 1, birthday: '1988-05-20', salary: 100000, educational: 5, mail: '0013@edojs.com', age: 36, country: 'CN', interest: '2,7', notes: ''},
    {ID: 'rose', department: 'JS', position: 'JS5', name: '露丝', gender: 2, married: 2, birthday: '1988-05-20', salary: 5500, educational: 2, mail: '0014@edojs.com', age: 28, country: 'EN', interest: '3,5', notes: ''},    
    {ID: 'owne', department: 'CW', position: 'CW2', name: '欧文', gender: 1, married: 2, birthday: '1978-05-20', salary: 8500, educational: 3, mail: '0015@edojs.com', age: 28, country: 'EN', interest: '1,5', notes: ''},
    {ID: 'rudy', department: 'SC', position: 'SC3', name: '鲁迪', gender: 2, married: 1, birthday: '1968-05-20', salary: 9000, educational: 4, mail: '0016@edojs.com', age: 34, country: 'CN', interest: '3,6', notes: ''}    
];

//供查询数据
var queryGenders = Genders.clone().insert(0,{id: -1, name: ''});
var queryCountrys = Countrys.clone().insert(0,{id: -1, name: ''});
var queryDepartments = Departments.clone().insert(0,{id: -1, name: ''});
var queryMarryStatus = MarryStatus.clone().insert(0,{id: -1, name: ''});
var queryEducationals = Educationals.clone().insert(0,{id: -1, name: ''});