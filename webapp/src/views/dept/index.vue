<template>
  <div style="margin: 10px;padding-left: 10px;">
    <el-button type="primary" @click="add">新增</el-button>
    <el-table :data="dataList">
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="deptName" label="部门名称"></el-table-column>
      <el-table-column prop="recordStatus" label="状态"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <dept-form :show.sync="form.show" :code.sync="form.code" :state.sync="form.state" @refresh="listData"></dept-form>
  </div>
</template>

<script>
  import deptForm from './deptForm';
  export default {
    name: "index",
    components: {
      deptForm
    },
    data() {
      return {
        dataList: [],
        form: {
          show: false,
          state: 'info',
          code:'',
        }
      }
    },
    created() {
      this.listData();
    },
    computed: {
    },
    watch: {
      $route() {
        this.listData();
      }
    },
    methods: {
      async listData() {
        this.dataList = await this.$get("/rbac/list-depts");
      },
      add() {
        this.form.state = 'add';
        this.form.code = '';
        this.form.show = true;
      },
      edit(item) {
        this.form.state = 'edit';
        this.form.code = item.deptCode;
        this.form.show = true;
      }
    }


  }
</script>

<style scoped>

</style>
