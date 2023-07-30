
$('#btn-login-confirmed').click(e => {

    const formData = new FormData(document.getElementById('login-form'));

    fetch('/user/login', {
        method: 'POST',
        body: formData
    }).then(response => response.json())
        .then(data => {
            // Xử lý kết quả từ server (nếu cần)
            if (data.code === 0) {
                Swal.fire("Thành Công", "Login thành công", "success");
                setTimeout(function () {
                    location.href = "";
                }, 2000);
            } else {
                Swal.fire("Thất Bại", data.message, "error");
            }
            console.log(data);
        })
        .catch(error => {
            // Xử lý lỗi (nếu có)
            console.error(error);
        });
})

$('#btn-register-confirmed').click(e => {

    const formData = new FormData(document.getElementById('register-form'));
    formData.append("role", "Khong sua duoc dau ahihi")
    fetch('/user/register', {
        method: 'POST',
        body: formData
    }).then(response => response.json())
        .then(data => {
            // Xử lý kết quả từ server (nếu cần)
            if (data.code === 0) {
                Swal.fire("Thành Công", data.message, "success");
                setTimeout(function () {
                    location.href = "";
                }, 2000);
            } else {
                Swal.fire("Thất Bại", data.message, "error");
            }
            console.log(data);
        })
        .catch(error => {
            // Xử lý lỗi (nếu có)
            console.error(error);
        });
})


$("#logout").click(function (e) {
    Swal.fire({
        title: "Bạn có muốn đăng xuất không?",
        // text: "You wont be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes",
        cancelButtonText: "No"
    }).then(function (result) {
        // Nếu người dùng bấm "Yes"
        if (result.isConfirmed) {
            // Thực hiện yêu cầu fetch ở đây
            fetch('/user/logout', {
                method: 'GET', // hoặc GET, PUT, DELETE tùy vào yêu cầu của bạn
                // Thêm các thông tin yêu cầu khác nếu cần
            })
                .then(response => response.json()) // Xử lý phản hồi từ fetch (nếu cần)
                .then(data => {
                    // Xử lý dữ liệu trả về từ fetch (nếu cần)
                    if (data.code === 0) {
                        if (result.value) {
                            Swal.fire(
                                "Thành công!",
                                "Đã đăng xuất thành công.",
                                "success"
                            );
                            setTimeout(function () {
                                location.href = "";
                            }, 1000);
                        }
                    }
                    console.log(data);
                })
                .catch(error => {
                    // Xử lý lỗi (nếu có)
                    console.error('Fetch error:', error);
                });
        }
    });
});
