<template>
  <Menu></Menu>
  <div class="container">

    <!-- 学生积分卡片 -->
    <Row :gutter="16" class="student-grid">
      <Col :span="24" :md="12" :lg="8" :xl="6" v-for="(student, index) in students" :key="student.name">
        <Card class="student-card">
          <div class="card-content">
            <Button type="error" @click="subtraction(index)" v-if="showButton"><Icon type="md-remove" /></Button>
            <span class="student-name">{{ student.name }}</span>
            <span class="score">{{ student.total }}</span>
            <Button type="success" @click="addition(index)" v-if="showButton"><Icon type="md-add" /></Button>
          </div>
        </Card>
      </Col>
    </Row>

    <Modal
        v-model="additionModal"
        title="加分操作"
        @on-ok="additionCheckModal = true"
        @on-cancel="cancel">
      <Form :model="additionFormItem" :label-width="50">
        <FormItem label="分数:">
          <RadioGroup v-model="additionFormItem.valueSelect">
            <Radio label="1" border >+1</Radio>
            <Radio label="2" border >+2</Radio>
            <Radio label="3" border >+3</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="学科:">
          <RadioGroup v-model="additionFormItem.subjectSelect" >
            <Radio label="语文" border></Radio>
            <Radio label="数学" border></Radio>
            <Radio label="英语" border></Radio>
            <Radio label="物理" border></Radio>
            <Radio label="化学" border></Radio>
            <Radio label="历史" border></Radio>
            <Radio label="道法" border></Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="原因:">
          <Input v-model="additionFormItem.reasonInput" placeholder="Enter reason..."></Input>
        </FormItem>
      </Form>
    </Modal>


    <Modal
        v-model="subtractionModal"
        title="扣分操作"
        @on-ok="subtractionCheckModal = true"
        @on-cancel="cancel">
      <Form :model="subtractionFormItem" :label-width="50">
        <FormItem label="分数:">
          <RadioGroup v-model="subtractionFormItem.valueSelect" >
            <Radio label="1" border>-1</Radio>
            <Radio label="2" border>-2</Radio>
            <Radio label="3" border>-3</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="学科:">
          <RadioGroup v-model="subtractionFormItem.subjectSelect" >
            <Radio label="语文" border></Radio>
            <Radio label="数学" border></Radio>
            <Radio label="英语" border></Radio>
            <Radio label="物理" border></Radio>
            <Radio label="化学" border></Radio>
            <Radio label="历史" border></Radio>
            <Radio label="道法" border></Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="原因:">
          <Input v-model="subtractionFormItem.reasonInput" placeholder="Enter reason..."></Input>
        </FormItem>
      </Form>
    </Modal>

    <Modal
        v-model="subtractionCheckModal"
        title="扣分操作确认"
        @on-ok="subtractionSubmit"
        @on-cancel="cancel">
        操作不可撤销,请检查信息!
        <p style="color: #ff9900">目标:{{this.clickStudentName}}</p>
        <p style="color: #ff9900">学科:{{this.subtractionFormItem.subjectSelect}}</p>
        <p style="color: #ff9900">原因:{{this.subtractionFormItem.reasonInput}}</p>
        <p style="color: #ed4014">扣除分数:-{{this.subtractionFormItem.valueSelect}}</p>
    </Modal>

    <Modal
        v-model="additionCheckModal"
        title="加分操作确认"
        @on-ok="additionSubmit"
        @on-cancel="cancel">
      操作不可撤销,请检查信息!
      <p style="color: #ff9900">目标:{{this.clickStudentName}}</p>
      <p style="color: #ff9900">学科:{{this.additionFormItem.subjectSelect}}</p>
      <p style="color: #ff9900">原因:{{this.additionFormItem.reasonInput}}</p>
      <p style="color: #ed4014">增加分数:+{{this.additionFormItem.valueSelect}}</p>
    </Modal>

  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import {Row, Col, Card, Button, Space, FormItem, Notice} from 'view-ui-plus';
import Menu from "@/components/Menu.vue";
import formItem from "view-ui-plus/src/components/form-item";
import {request} from "@/plugins/axios";
import {getCookie, setCookie} from "typescript-cookie";
import router from "@/router";
import getLoginState from "@/api/getLoginState";

let message = ref("");

export default {
  data (){
    return {
      additionFormItem: {
        valueSelect: '',
        subjectSelect: '',
        reasonInput: ''
      },
      subtractionFormItem: {
        valueSelect: '',
        subjectSelect: '',
        reasonInput: ''
      },
      //加分操作面板显示
      additionModal : false,
      //扣分面板显示
      subtractionModal : false,
      //确认面板显示
      additionCheckModal : false,
      subtractionCheckModal : false,
      //正在执行操作的目标学生姓名
      clickStudentName: '',
    }
  },
  methods : {
    addition(index){
      //显示窗口
      this.additionModal = true;
      this.clickStudentName = this.students[index].name
    },
    subtraction(index){
      this.subtractionModal = true;
      this.clickStudentName = this.students[index].name
    },
    additionSubmit(){
      request.post('points/addition', {

        "target": this.clickStudentName,
        "subject": this.additionFormItem.subjectSelect,
        "value": Number(this.additionFormItem.valueSelect),
        "reason": this.additionFormItem.reasonInput

      }).then(r => {
        message.value = r.data.msg;
        if (r.data.code === 200) {
          Notice.success({
            title: '加分成功',
            desc: message.value,
          });
          this.fetchPoints()
          this.clean()
        }
        if (r.data.code === 403) {
          Notice.error({
            title: '加分失败',
            desc: message.value,
          });
          this.fetchPoints()
          this.clean()
        }
      });

    },
    subtractionSubmit(){
      request.post('points/subtraction', {

        "target": this.clickStudentName,
        "subject": this.subtractionFormItem.subjectSelect,
        "value": Number(this.subtractionFormItem.valueSelect),
        "reason": this.subtractionFormItem.reasonInput

      }).then(r => {
        message.value = r.data.msg;
        if (r.data.code === 200) {
          Notice.success({
            title: '扣分成功',
            desc: message.value,
          });
          this.fetchPoints()
          this.clean()
        }
        if (r.data.code === 403) {
          Notice.error({
            title: '扣分失败',
            desc: message.value,
          });
          this.fetchPoints()
          this.clean()
        }
      });

    },
    cancel () {
      this.clean()
      //重制点击学生
      this.clickStudentName = '';
      //提示
      this.$Message.info('Cancel!');
    },
    clean(){
      this.clickStudentName = '';

      this.additionFormItem.subjectSelect = '';
      this.subtractionFormItem.subjectSelect = '';

      this.subtractionFormItem.reasonInput = '';
      this.additionFormItem.reasonInput = '';

      this.subtractionFormItem.valueSelect = '';
      this.additionFormItem.valueSelect = '';
    },

  },
  components: {
    FormItem,
    Space,
    Menu,
    Row,
    Col,
    Card,
    Button
  },
  setup() {
    let showButton = ref(false);

    // 初始化学生数据
    const students = ref([
      { name: "曹燕琳", total: 0 },
      { name: "陈雨涵", total: 0 },
      { name: "董漪澜", total: 0 },
      { name: "何思萱", total: 0 },
      { name: "何易璇", total: 0 },
      { name: "黄诗禹", total: 0 },
      { name: "姜乐幽", total: 0 },
      { name: "姜若瑶", total: 0 },
      { name: "李采扬", total: 0 },
      { name: "李思锐", total: 0 },
      { name: "李翊萱", total: 0 },
      { name: "鲁晨熙", total: 0 },
      { name: "庞宇轩", total: 0 },
      { name: "彭怡然", total: 0 },
      { name: "孙炜琦", total: 0 },
      { name: "孙一铎", total: 0 },
      { name: "王涵钥", total: 0 },
      { name: "王若曦", total: 0 },
      { name: "吴旻", total: 0 },
      { name: "吴一凡", total: 0 },
      { name: "肖佳妮", total: 0 },
      { name: "谢艾希", total: 0 },
      { name: "张天语", total: 0 },
      { name: "张怡恬", total: 0 },
      { name: "曹溢", total: 0 },
      { name: "高晓斯", total: 0 },
      { name: "郭子禾", total: 0 },
      { name: "何简至", total: 0 },
      { name: "黄琛贺", total: 0 },
      { name: "金骏浩", total: 0 },
      { name: "刘博渊", total: 0 },
      { name: "陆俊毅", total: 0 },
      { name: "任镕均", total: 0 },
      { name: "施皓璋", total: 0 },
      { name: "孙一诺", total: 0 },
      { name: "孙羿博", total: 0 },
      { name: "魏子博", total: 0 },
      { name: "吴明灏", total: 0 },
      { name: "谢金熠", total: 0 },
      { name: "杨子轩", total: 0 },
      { name: "袁振林", total: 0 },
      { name: "周子轩", total: 0 }
    ]);

    const fetchPoints = async () => {
      const token = getCookie("token");
      if (!token) {
        console.error("Token 不存在，无法获取积分");
        return;
      }

      try {
        students.value = await Promise.all(
            students.value.map(async (student) => {
              const response = await request.get(`/user/getPointByName?name=${student.name}`);
              return {...student, total: response.data}; // 更新积分
            })
        ); // 更新整个数组
      } catch (error) {
        console.error("获取积分失败:", error);
      }
    };
    const checkShowButton = () => {
      request.get('/user/getAdministratorByToken/?token=' + getCookie('token')).then(r => {
        let result = r.data

        if (result === 2 || result === 1) {
          showButton.value = true;
        } else if (result === 0) {
          showButton.value = false;
        } else if (result === -1) {
          Notice.error({
            title: '未登录',
          });
          router.replace('/login');
        }
      });
    };


    // 加载本地存储数据
    onMounted(() => {
      checkShowButton()
      fetchPoints();
      getLoginState();
    });

    return {
      checkShowButton,
      showButton,
      students,
      fetchPoints,
    };
  }
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  color: #2c3e50;
  text-align: center;
  margin-bottom: 30px;
}

.student-grid {
  margin-bottom: 30px;
}

.student-card {
  margin-bottom: 16px;
}

.card-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.student-name {
  flex-grow: 1;
  margin: 0 15px;
  color: #34495e;
  font-weight: 500;
}

.score {
  min-width: 40px;
  text-align: center;
  font-weight: bold;
  color: #2c3e50;
}

.reset-btn {
  display: block;
  margin: 30px auto;
  padding: 12px 24px;
}
</style>