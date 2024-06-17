const tableBody = document.querySelector("#odontologosTable tbody");
// listando los odontologos
function fetchOdontologos() {
  fetch(`/odontologo`)
    .then(response => response.json())
    .then(data => {
      console.log(data);

      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${index + 1}</td>
          <td>${odontologo.nombre}</td>
          <td>${odontologo.apellido}</td>
          <td>${odontologo.matricula}</td>
          <td>
            <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.matricula}')">Modificar</button>
            <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
          </td>
        `;
        tableBody.appendChild(row);
      });
    })
    .catch(error => {
      console.error("Error fetching data:", error);
    });
}

function editOdontologo(id, nombre, apellido, matricula) {
  document.getElementById("editId").value = id;
  document.getElementById("editNombre").value = nombre;
  document.getElementById("editApellido").value = apellido;
  document.getElementById("editMatricula").value = matricula;
  new bootstrap.Modal(document.getElementById("editModal")).show();
}

document
.getElementById("editForm")
.addEventListener("submit", function(event) {
  event.preventDefault();
  const id = document.getElementById("editId").value;
  const nombre = document.getElementById("editNombre").value;
  const apellido = document.getElementById("editApellido").value;
  const matricula = document.getElementById("editMatricula").value;

  fetch(`/odontologo`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ id, nombre, apellido, matricula }),
  })
    .then(response => response.json())
    .then(data => {
      console.log(data);
      alert("Odontólogo modificado con éxito");
      fetchOdontologos();
      bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
    })
    .catch(error => {
      console.error("Error editando odontólogo:", error);
    });
});

function deleteOdontologo(id) {
  if (confirm("¿Estás seguro de que deseas eliminar este odontólogo?")) {
    fetch(`/odontologo/${id}`, {
      method: "DELETE",
    })
      .then(response => response.json())
      .then(data => {
        console.log(data);
        alert("Odontólogo eliminado con éxito");
        fetchOdontologos();
      })
      .catch(error => {
        console.error("Error eliminando odontólogo:", error);
      });
  }
}

fetchOdontologos();
