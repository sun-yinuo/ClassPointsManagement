<template>
  <Menu></Menu>
  <div class="container">
    <!-- 过滤选择器 -->
    <div class="filter-bar">
      <!-- 第一层筛选：审核状态 -->
      <Select v-model="selectedAuditStatus" placeholder="选择审核状态" style="width: 200px; margin-right: 10px;">
        <Option label="全部" value="all" />
        <Option label="未审核" value="unreviewed" />
        <Option label="已审核" value="reviewed" />
        <Option label="审核通过" value="approved" />
        <Option label="审核未通过" value="rejected" />
      </Select>

      <!-- 第二层筛选：事件类型 -->
      <Select v-model="selectedEventType" placeholder="选择事件类型" style="width: 200px;">
        <Option label="全部" value="all" />
        <Option label="积分加" :value="2" />
        <Option label="积分减" :value="3" />
      </Select>
    </div>

    <!-- 卡片列表使用 transition-group 实现进入/离场动画 -->
    <transition-group name="card" tag="div">
      <div class="card-item" v-for="item in filteredEvents" :key="item.eventId">
        <Card class="audit-card" :style="getCardStyle(item.eventType)">
          <div class="card-content">
            <!-- 左侧信息区域 -->
            <div class="info">
              <div class="header">
                <span class="event-id">#{{ item.eventId }}</span>
                <span class="event-type">{{ translateEventType(item.eventType) }}</span>
                <span class="event-time">{{ formatDate(item.time) }}</span>
              </div>
              <div class="detail">
                <p><strong>执行人：</strong>{{ item.operator }}</p>
                <p><strong>被执行人：</strong>{{ item.target }}</p>
                <p><strong>原因：</strong>{{ item.reason }}</p>
                <p><strong>学科：</strong>{{ item.subject }}</p>
                <p>
                  <strong>审核状态：</strong>
                  <span v-if="item.pass === -1">未审核</span>
                  <span v-else-if="item.pass === 1">通过</span>
                  <span v-else-if="item.pass === 0">拒绝</span>
                </p>
              </div>
            </div>
            <!-- 右侧操作区域，仅当事件未审核时显示操作按钮 -->
            <div class="actions" v-if="item.pass === -1">
              <Button type="success" size="large" @click="approveEvent(item)" class="action-btn">通过</Button>
              <Button type="error" size="large" @click="rejectEvent(item)" class="action-btn">拒绝</Button>
            </div>
          </div>
        </Card>
      </div>
    </transition-group>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount, computed } from 'vue';
import { Card, Button, Select, Option, Message } from 'view-ui-plus';
import { request } from '@/plugins/axios';
import Menu from "@/components/Menu.vue";

export default {
  name: 'AdminAudit',
  components: {Menu, Card, Button, Select, Option },
  setup() {
    const events = ref([]);
    const selectedAuditStatus = ref("unreviewed"); // 第一层筛选：审核状态
    const selectedEventType = ref("all");   // 第二层筛选：事件类型
    let refreshInterval = null;

    // 获取审核事件列表，接口地址改为 /point-events/getAuditEvents
    const fetchEvents = async () => {
      try {
        const response = await request.get('/point-events/getAuditEvents');
        events.value = response.data;
      } catch (error) {
        console.error('获取审核事件失败:', error);
      }
    };

    // 审核通过后调用接口，并移除该卡片
    const approveEvent = async (item) => {
      try {
        await request.post('/point-events/updateAuditEvent', {
          eventId: item.eventId,
          pass: 1
        });
        Message.success('审核通过');
        removeEvent(item.eventId);
      } catch (error) {
        console.error('审核通过失败:', error);
        Message.error('审核操作失败');
      }
    };

    // 审核拒绝后调用接口，并移除该卡片
    const rejectEvent = async (item) => {
      try {
        await request.post('/point-events/updateAuditEvent', {
          eventId: item.eventId,
          pass: 0
        });
        Message.success('审核拒绝');
        removeEvent(item.eventId);
      } catch (error) {
        console.error('审核拒绝失败:', error);
        Message.error('审核操作失败');
      }
    };

    // 从 events 数组中移除审核后的项（触发动画）
    const removeEvent = (eventId) => {
      const index = events.value.findIndex(item => item.eventId  === eventId);
      if (index !== -1) {
        events.value.splice(index, 1);
      }
    };

    // 时间戳转换为 YYYY-M-D 格式
    const formatDate = (timestamp) => {
      const date = new Date(Number(timestamp));
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      return `${year}-${month}-${day}`;
    };

    // 翻译事件类型
    const translateEventType = (type) => {
      switch(Number(type)) {
        case 1:
          return '用户登陆注册';
        case 2:
          return '积分加';
        case 3:
          return '积分减';
        case 4:
          return '重制积分';
        case 5:
          return '班主任审核通过';
        case 6:
          return '班主任拒绝';
        default:
          return '未知类型';
      }
    };

    // 根据事件类型返回不同的背景渐变样式（科技感、饱和度较低）
    const getCardStyle = (eventType) => {
      let gradient = 'linear-gradient(135deg, #ffffff, #e6f7ff)';
      if (Number(eventType) === 2) { // 积分加 - 绿色
        gradient = 'linear-gradient(135deg, #e8f5e9, #c8e6c9)';
      } else if (Number(eventType) === 3) { // 积分减 - 红色
        gradient = 'linear-gradient(135deg, #ffebee, #ffcdd2)';
      }
      return {
        background: gradient
      };
    };

    // 筛选后的事件列表
    const filteredEvents = computed(() => {
      return events.value.filter(item => {
        // 第一层筛选：审核状态
        const auditStatusMatch = selectedAuditStatus.value === "all" ||
            (selectedAuditStatus.value === "unreviewed" && item.pass === -1) ||
            (selectedAuditStatus.value === "reviewed" && item.pass !== -1) ||
            (selectedAuditStatus.value === "approved" && item.pass === 1) ||
            (selectedAuditStatus.value === "rejected" && item.pass === 0);

        // 第二层筛选：事件类型
        const eventTypeMatch = selectedEventType.value === "all" ||
            Number(item.eventType) === Number(selectedEventType.value);

        return auditStatusMatch && eventTypeMatch;
      });
    });

    onMounted(() => {
      fetchEvents();
      refreshInterval = setInterval(fetchEvents, 5000);
    });
    onBeforeUnmount(() => {
      if (refreshInterval) clearInterval(refreshInterval);
    });

    return {
      events,
      selectedAuditStatus,
      selectedEventType,
      filteredEvents,
      approveEvent,
      rejectEvent,
      formatDate,
      translateEventType,
      getCardStyle
    };
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
  //background: #f5f7fa;
}

/* 顶部过滤条 */
.filter-bar {
  margin-bottom: 20px;
}

/* 动画效果：进入淡入并上移，离场时淡出并上移 */
.card-enter-active, .card-leave-active {
  transition: all 0.5s ease;
}
.card-enter-from, .card-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

.card-item {
  margin-bottom: 20px;
}

/* 卡片样式：渐变背景、阴影、圆角 */
.audit-card {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  overflow: hidden;
}

/* 卡片内容：横向布局 */
.card-content {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 20px;
}

/* 左侧信息区域 */
.info {
  flex: 1;
}

/* 头部信息 */
.header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.event-id {
  color: #888;
  font-weight: bold;
  margin-right: 15px;
  font-size: 16px;
}

.event-type {
  font-weight: 500;
  color: #333;
  margin-right: 15px;
  font-size: 16px;
}

.event-time {
  color: #999;
  font-size: 14px;
}

/* 详情信息 */
.detail p {
  margin: 4px 0;
  font-size: 14px;
  color: #555;
}

/* 右侧操作区域 */
.actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-left: 20px;
}

.action-btn {
  margin: 10px 0;
  min-width: 120px;
  font-size: 16px;
}

/* 手机端适配：堆叠布局 */
@media (max-width: 768px) {
  .card-content {
    flex-direction: column;
    align-items: flex-start;
  }
  .actions {
    flex-direction: row;
    width: 100%;
    justify-content: space-around;
    margin-top: 15px;
    margin-left: 0;
  }
  .action-btn {
    margin: 0 5px;
    min-width: 100px;
    font-size: 14px;
  }
}
</style>
