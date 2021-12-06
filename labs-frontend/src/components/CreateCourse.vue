<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-12 col-sm-10 col-md-8 offset-sm-1 offset-md-2">
        <div class="mt-5">
          <form
            class="border border-primary rounded form-inline"
            @submit="createcourse"
          >
            <h2 class="col-12 text-center text-primary mt-3 mb-5">
              Crear curso
            </h2>

            <div class="form-group col-12">
              <label for="course_name" class="custom-label col-md-3"
                >Nombre de curso</label
              >
              <input
                id="course_name"
                class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
                type="text"
                placeholder="Nombre"
                v-model="course_name"
                required
              />
            </div>

            <div class="form-group col-12">
              <label for="duration" class="custom-label col-md-3"
                >Duración</label
              >
              <input
                id="course_duration"
                class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
                type="number"
                placeholder="Duracion"
                v-model="course_duration"
                required
              />
            </div>

            <div class="col-12 col-sm-5 col-md-4 mb-3">
              <button
                class="col-sm-10 col-md-10 offset-sm-1 offset-md-2 btn btn-primary"
                type="submit"
              >
                Crear curso
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { getAuthenticationToken } from "@/dataStorage";
export default {
  name: "CreateCourse",
  data() {
    return {
      course_name: "",
      course_duration: "",
    };
  },
  methods: {
    createcourse(event) {
      const requestPath = "/profesor/crear-curso";
      axios
        .post(
          this.$store.state.backURL + requestPath,
          {
            courseName: this.course_name,
            durationHours: this.course_duration.toString(),
          },
          { params: { access_token: getAuthenticationToken() } }
        )
        .then((response) => {
          if (response.status === 400) {
            alert("Formato de curso incorrecto");
          }
          this.$router.replace({name: "home"});
        })
        .catch((error) => {
          alert("Error en la petición");
          console.log(error);
        });
      event.preventDefault();
    },
  },
};
</script>

<style></style>
