<template>
  <div>
    <Menu></Menu>
    <Row justify="center">
      <Col span="6">
        <Select v-model="selectedType" placeholder="筛选事件类型" @on-change="filterEvents">
          <Option v-for="type in eventTypes" :key="type.value" :value="type.value">
            {{ type.label }}
          </Option>
        </Select>
      </Col>
    </Row>
    <Table :columns="columns" :data="filteredEvents" border></Table>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted, computed } from "vue";
import {request} from "@/plugins/axios";
import dayjs from "dayjs";
import Menu from "@/components/Menu.vue";

export default defineComponent({
  components: {Menu},
  setup() {
    const events = ref([]);
    const selectedType = ref(null);

    const eventTypes = [
      { value: 2, label: "积分加" },
      { value: 3, label: "积分减" },
      { value: 5, label: "班主任审核通过" },
      { value: 6, label: "班主任拒绝" }
    ];

    const eventTypeMap = {
      2: "积分加",
      3: "积分减",
      5: "班主任审核通过",
      6: "班主任拒绝"
    };

    const columns = [
      {title: "事件ID", key: "id", align: "center"},
      {
        title: "事件类型",
        key: "eventType",
        align: "center",
        render: (h, params) => h("span", eventTypeMap[params.row.eventType] || "未知")
      },
      {
        title: "事件时间", key: "time", align: "center", render: (h, params) => {
          const timeValue = params.row.time;
          const formattedTime = dayjs(timeValue).isValid()
              ? dayjs(timeValue * (timeValue.toString().length === 10 ? 1000 : 1)).format("YYYY-MM-DD HH:mm:ss")
              : "无效时间";
          return h("span", formattedTime);
        }
      },
      {title: "执行人", key: "operator", align: "center"},
      {title: "被执行人", key: "target", align: "center"},
      {title: "描述", key: "event", align: "center"},
      {title: "学科", key: "subject", align: "center"},
      {title: "原因", key: "reason", align: "center"}
    ];

    const fetchEvents = async () => {
      try {
        const response = await request.get("/events/getEventsList");
        events.value = response.data;
      } catch (error) {
        console.error("获取事件列表失败", error);
      }
    };

    const filteredEvents = computed(() => {
      return selectedType.value
          ? events.value.filter(event => event.eventType === selectedType.value)
          : events.value;
    });


    onMounted(fetchEvents);

    return {events, selectedType, eventTypes, eventTypeMap, columns, filteredEvents, filterEvents: fetchEvents};
  }
});
</script>

<style scoped>
@media (max-width: 768px) {
  .ivu-table td,
  .ivu-table th {
    font-size: 12px;
    padding: 8px;
  }
}
</style>
