<template>
  <el-dialog :visible.sync="dialogShow" :title="dialogTitle">
    <el-form :model="formItem" ref="form" label-width="80px">
      <el-form-item label="昵称" required>
        <el-input v-model="formItem.nickName"></el-input>
      </el-form-item>
      <el-form-item label="手机号" required>
        <el-input v-model="formItem.userName"></el-input>
      </el-form-item>
      <el-form-item label="密码" required v-if="state === 'add'">
        <el-input v-model="formItem.password"></el-input>
      </el-form-item>
      <el-form-item label="状态" v-if="state === 'edit'">
        <el-select v-model="formItem.recordStatus" placeholder="请选择">
          <el-option label="正常" :value="1"></el-option>
          <el-option label="禁用" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="门店" required>
        <el-select v-model="formItem.deptCode" clearable placeholder="请选择">
          <el-option :label="dept.deptName" :value="dept.deptCode" v-for="dept in deptList"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import Avatar from "vue-avatar/src/Avatar";

  export default {
    name: "userForm",
    components: {Avatar},
    props: {
      show: {
        type: Boolean,
        default: false
      },
      state: {
        type: String,
        default: 'info'
      },
      code: {
        type: String,
        required: false,
        default: ''
      }
    },
    data() {
      return {
        formItem: {},
        deptList: []
      }
    },
    computed: {
      dialogTitle() {
        switch (this.state) {
          case 'info':
            return '查看用户';
          case 'edit':
            return '编辑用户';
          case 'add':
            return '新增用户';
        }
      },
      dialogShow: {
        get() {
          return this.show;
        },
        set(val) {
          this.$emit('update:show', val);
        }
      }
    },
    watch: {
      async dialogShow(newVal) {
        this.formItem = {};
        this.deptList = await this.$get('/rbac/list-depts');
        if (newVal && this.code) {
          this.formItem = await this.$get('/rbac/get-user', {userCode: this.code})
        }
      }
    },
    methods: {
      close() {
        this.dialogShow = false
      },
      async save() {
        const url = this.state === 'add' ? '/rbac/add-user' : '/rbac/edit-user';
        await this.$put(url, this.formItem, true);
        this.$message.success('保存成功');
        this.close();
        this.$emit('refresh');
      }
    }
  }
</script>

<style scoped>

</style>
