function eliminar(id) {
	swal({
		title: "Estas seguro de eliminar?",
		text: "Se eliminara el siguiente registro!",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((OK) => {
			if (OK) {
				$.ajax({
					url:"/producto/eliminar/"+id,
					success: function(res) {
						console.log(res);
					},
				});
				swal("Poof! Haz eliminado el registro!", {
					icon: "success",
				}).then((ok) => {
					if (ok) {
						location.href = "/producto/listar";
					}
				});
			} else {
				swal("Registro no borrado");
			}
		});
}