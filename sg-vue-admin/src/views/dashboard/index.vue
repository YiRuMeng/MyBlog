<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      水鱼博客管理系统
    </div>
    <div>
      欢迎您的到来O_O   忆茹梦
    </div>
    <div>
      邮箱：2390578687@qq.com
	  微信：hjjo810
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
