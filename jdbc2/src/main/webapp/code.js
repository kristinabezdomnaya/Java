$(document).ready(() => {

	const form = $('#employeeForm')[0];
	const modal = $('#employeeModal');

	const employeeData = $('#employeeList').DataTable({
		"serverSide": true,
		"lengthChange": true,
		"processing": false,
		"order":[1],
		"ajax":{
			url: "/api/employees",
			type: "GET"
		},

		"columns": [
			{ "searchable": false, "orderable": false },
			{ "searchable": true },
			{ "searchable": false },
			{ "searchable": false },
			{
				"mData": null,
				"bSortable": false,
				"mRender": (row) => {
					const id = row[0];

					return `<button 
  						type="button" name="update" id="${id}" 
  						class="btn btn-warning btn-xs update">Update</button>`;
				}
			},
			{
				"mData": null,
				"bSortable": false,
				"mRender": (row) => {
					const id = row[0];

					return `<button type="button" name="delete" id="${id}" 
                            class="btn btn-danger btn-xs delete">Delete</button>`;
				}
			}
		],

		"pageLength": 10,
		"bInfo": false,
	});		

	$('#addEmployee').click(() => {
		form.reset();
		modal.modal('show');
	});

	$("#employeeModal").on('submit', '#employeeForm', (event) => {
		event.preventDefault();

		const data = readFromForm(form);
		const url = "/api/employees";
		const method =  data.id ? "PUT" : "POST";
		const callback = (data) => {
			modal.modal('hide');
			form.reset();
			employeeData.ajax.reload();
		};

		request(url, method, data, callback);
	});

	$("#employeeList")
		.on('click', '.update', (event) => {
			const url = '/api/employees?id=' + $(event.target).attr("id");

			request(url, 'GET', null, (data) => {
				writeToForm(form, data);
				modal.modal('show');
			});
		})
		.on('click', '.delete', (event) => {

		if(!confirm('Are you sure you want to delete this employee?')) {
			return false;
		}

		const url = '/api/employees?id=' + $(event.target).attr('id');

		request(url, 'DELETE', null, () => employeeData.ajax.reload());
	});

	function request(url, method, data, callback) {

		const params = {
			url: url,
			method: method,
			success: callback,
			error: (e) => console.log('error: ' + JSON.stringify(e))
		};

		if (method !== 'DELETE') {
			params.dataType = "json";
			params.contentType= "application/json; charset=utf-8";
		}

		if (data) {
			params.data = JSON.stringify(data);
		}

		$.ajax(params);
	}

	function writeToForm(form, data) {
		for (const key of Object.keys(data)) {
			$(form[key]).val(data[key]);
		}
	}

	function readFromForm(form) {

		const keys = $(form).find('input').toArray()
			.filter(f => f.type !== 'submit')
			.map(f => f.name);

		const data =  {};

		for (const key of keys) {
			data[key] = $(form[key]).val();
		}

		return data;
	}

});