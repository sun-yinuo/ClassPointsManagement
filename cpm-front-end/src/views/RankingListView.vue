<style>
.table-container {
  width: 100%;

  margin: 20px auto;
  padding: 0 10px;
  box-sizing: border-box;
}

/* 流动动画 keyframes */
@keyframes flow {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

/* 前三名行样式 */
.ivu-table .first-row td{
  background: linear-gradient(270deg, #fff3e0, #ffe0b2, #fff3e0);
  background-size: 600% 600%;
  animation: flow 4s ease infinite;
}
.ivu-table .second-row td{
  background: linear-gradient(270deg, #f5f5f5, #e0e0e0, #f5f5f5);
  background-size: 600% 600%;
  animation: flow 4s ease infinite;
}
.ivu-table .third-row td{
  background: linear-gradient(270deg, #e0f7fa, #b2ebf2, #e0f7fa);
  background-size: 600% 600%;
  animation: flow 4s ease infinite;
}
</style>
<template>
  <Menu />
  <div class="table-container">
    <!-- 给 Table 添加 row-class-name 属性 -->
    <Table
        style="width: 100%;"
        :columns="columns"
        :data="sortedStudents"
        border
        :row-class-name="rowClassName"
    >
      <!-- 自定义学生姓名列模板 -->
      <template #name="{ row }">
        <strong>{{ row.name }}</strong>
      </template>
      <!-- 自定义积分列模板 -->
      <template #total="{ row }">
        <span>{{ row.total }}</span>
      </template>
    </Table>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { Table } from 'view-ui-plus';
import { request } from '@/plugins/axios';
import { getCookie } from 'typescript-cookie';
import Menu from "@/components/Menu.vue";

export default {
  components: {
    Menu,
    Table,
  },
  setup() {
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

    // 表头配置
    const columns = [
      {
        title: '姓名',
        key: 'name',
        slot: 'name'
      },
      {
        title: '积分',
        key: 'total',
        slot: 'total'
      }
    ];

    // 根据行索引返回对应的行样式
    const rowClassName = (row, index) => {
      if (index === 0) return 'first-row';
      if (index === 1) return 'second-row';
      if (index === 2) return 'third-row';
      return '';
    };

    // 获取积分数据（示例：使用接口更新积分）
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
              return { ...student, total: response.data }; // 假设接口返回积分数
            })
        );
      } catch (error) {
        console.error("获取积分失败:", error);
      }
    };

    // 根据积分降序排序
    const sortedStudents = computed(() => {
      return [...students.value].sort((a, b) => b.total - a.total);
    });




    onMounted(() => {
      fetchPoints();

    });

    return {
      columns,
      sortedStudents,
      rowClassName,
    };
  },
};
</script>


