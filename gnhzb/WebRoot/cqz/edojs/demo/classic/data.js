var Genders = [
    {id: 1, name: '男'},
    {id: 2, name: '女'}
];

var Countrys = [
    {id: 'CN', name: '中国'},
    {id: 'USA', name: '美国'},
    {id: 'EN', name: '英国'},
    {id: 'JP', name: '日本'}
]

var Interests = [
    {id: 1, name: '游戏'},
    {id: 2, name: '小说'},
    {id: 3, name: '足球'},
    {id: 4, name: '旅游'},
    {id: 5, name: '摄影'},
    {id: 6, name: '蹦极'},
    {id: 7, name: '音乐'}
];

var queryGenders = Genders.clone().insert(0,{id: -1, name: ''});
var queryCountrys = Countrys.clone().insert(0,{id: -1, name: ''});