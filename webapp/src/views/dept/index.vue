<template>
  <div style="margin: 10px;padding-left: 10px;">
    <el-button type="primary" @click="add">新增</el-button>
    <el-table :data="dataList">
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="deptName" label="门店名称"></el-table-column>
      <el-table-column prop="recordStatus" label="状态"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="del(scope.row)">删除</el-button>
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
      },
      del(item) {
        this.$confirm('删除门店, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$delete('/rbac/delete-dept',{deptCode:item.deptCode}).then(() => {
            this.$message.success('删除成功');
            this.listData();
          });
        }).catch(() => {
        });
      }
    }


  }
</script>

<style scoped>

</style>
