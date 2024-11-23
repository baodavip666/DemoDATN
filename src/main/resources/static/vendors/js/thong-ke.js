async function thongKeSpDonHangNam(){
    var nam = document.getElementById("hoadonsanphamnam").value
    var url = 'http://localhost:8080/api/thong-ke/public/thong-ke-nam?nam=' + nam;
    const response = await fetch(url, {
    });
    var arr = await response.json();
    var soluongdonhang = arr[0];
    var soluongsp = arr[1];
    var chart = document.getElementById("combinedChart").getContext('2d');
    const myChart = new Chart(chart, {
        type: 'bar',
        data: {
            labels: ["tháng 1", "tháng 2", "tháng 3", "tháng 4",
                "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"],
            datasets: [
                {
                    label: "Đơn hàng",
                    data: soluongdonhang, // Dữ liệu cho cột đầu tiên
                    backgroundColor: 'rgba(255, 99, 132, 0.2)', // Màu sắc cho cột
                    borderColor: 'rgba(255, 99, 132, 1)', // Màu viền cho cột
                    borderWidth: 1
                },
                {
                    label: "Sản phẩm",
                    data: soluongsp, // Dữ liệu cho cột thứ hai
                    backgroundColor: 'rgba(54, 162, 235, 0.2)', // Màu sắc cho cột
                    borderColor: 'rgba(54, 162, 235, 1)', // Màu viền cho cột
                    borderWidth: 1
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

}




async function thongKeSpDonHangThang(){
    var nam = document.getElementById("hoadonspnam").value
    var thang = document.getElementById("hoadonspthang").value
    var url = 'http://localhost:8080/api/thong-ke/public/thong-ke-thang?nam=' + nam+'&thang='+thang;
    const response = await fetch(url, {
    });
    var arr = await response.json();
    var soluongdonhang = arr[0];
    var soluongsp = arr[1];
    for(i=0; i<soluongsp.length; i++){
        if(soluongsp[i].soLuong == null){
            soluongsp[i].soLuong = 0;
        }
    }
    const days = [];
    const daysInMonth = new Date(nam, thang + 1, 0).getDate();
    for (let day = 1; day <= daysInMonth; day++) {
        days.push(day); // Thêm số ngày vào mảng
    }
    var dataSlDonHang = [];
    var dataSlSanPham = [];
    for(i=0; i<soluongdonhang.length; i++){
        dataSlDonHang.push(soluongdonhang[i].soLuong);
    }
    for(i=0; i<soluongsp.length; i++){
        dataSlSanPham.push(soluongsp[i].soLuong);
    }
    var chart = document.getElementById("canvasThang").getContext('2d');
    const myChart = new Chart(chart, {
        type: 'bar',
        data: {
            labels: days,
            datasets: [
                {
                    label: "Đơn hàng",
                    data: dataSlDonHang, // Dữ liệu cho cột đầu tiên
                    backgroundColor: 'rgba(255, 99, 132, 0.2)', // Màu sắc cho cột
                    borderColor: 'rgba(255, 99, 132, 1)', // Màu viền cho cột
                    borderWidth: 1
                },
                {
                    label: "Sản phẩm",
                    data: dataSlSanPham, // Dữ liệu cho cột thứ hai
                    backgroundColor: 'rgba(54, 162, 235, 0.2)', // Màu sắc cho cột
                    borderColor: 'rgba(54, 162, 235, 1)', // Màu viền cho cột
                    borderWidth: 1
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

}


async function thongKeSpDonHangNgay(){
    var tuNgay = document.getElementById("tuNgay").value
    var denNgay = document.getElementById("denNgay").value

    var url = 'http://localhost:8080/api/thong-ke/public/thong-ke-ngay?start=' + tuNgay+'&end='+denNgay;
    const response = await fetch(url, {
    });
    var arr = await response.json();

    var soluongdonhang = arr[0];
    var soluongsp = arr[1];

    for(i=0; i<soluongsp.length; i++){
        if(soluongsp[i].soLuong == null){
            soluongsp[i].soLuong = 0;
        }
    }

    const dates = [];

    // Tạo bản sao của startDate để không làm thay đổi giá trị ban đầu
    const currentDate = new Date(tuNgay);
    var endDate = new Date(denNgay);

    // Duyệt qua từng ngày cho đến khi đến ngày kết thúc
    while (currentDate <= endDate) {
        // Định dạng ngày dưới dạng "ngày X"
        dates.push(`ngày ${currentDate.getDate()}`);
        // Cộng thêm 1 ngày
        currentDate.setDate(currentDate.getDate() + 1);
    }


    var dataSlDonHang = [];
    var dataSlSanPham = [];
    for(i=0; i<soluongdonhang.length; i++){
        dataSlDonHang.push(soluongdonhang[i].soLuong);
    }
    for(i=0; i<soluongsp.length; i++){
        dataSlSanPham.push(soluongsp[i].soLuong);
    }
    var chart = document.getElementById("canvasNgay").getContext('2d');
    const myChart = new Chart(chart, {
        type: 'bar',
        data: {
            labels: dates,
            datasets: [
                {
                    label: "Đơn hàng",
                    data: dataSlDonHang, // Dữ liệu cho cột đầu tiên
                    backgroundColor: 'rgba(255, 99, 132, 0.2)', // Màu sắc cho cột
                    borderColor: 'rgba(255, 99, 132, 1)', // Màu viền cho cột
                    borderWidth: 1
                },
                {
                    label: "Sản phẩm",
                    data: dataSlSanPham, // Dữ liệu cho cột thứ hai
                    backgroundColor: 'rgba(54, 162, 235, 0.2)', // Màu sắc cho cột
                    borderColor: 'rgba(54, 162, 235, 1)', // Màu viền cho cột
                    borderWidth: 1
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

}



async function doanhThuNam(){
    var nam = document.getElementById("chondoanhthunam").value
    var url = 'http://localhost:8080/api/thong-ke/public/doanh-thu-nam?nam=' + nam;
    const response = await fetch(url, {
    });
    var list = await response.json();
    document.getElementById("bieudodoanhthunam").innerHTML = `<canvas id="doanhthunam"></canvas>`
    var chart = document.getElementById("doanhthunam").getContext('2d');
    const myChart = new Chart(chart, {
        type: 'bar',
        data: {
            labels: ["tháng 1", "tháng 2", "tháng 3", "tháng 4",
                "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"
            ],
            datasets: [{
                label: "Doanh thu năm "+nam,
                backgroundColor: 'rgba(161, 198, 247, 1)',
                borderColor: 'rgb(47, 128, 237)',
                data: list,
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        callback: function(value) {
                            return formatmoney(value);
                        }
                    }
                }]
            }
        },
    });
}



async function tangTruongDoanhThu(){
    var nam = document.getElementById("tangtruongdoanhthunam").value
    var url = 'http://localhost:8080/api/thong-ke/public/tang-truong-doanh-thu?nam=' + nam;
    const response = await fetch(url, {
    });
    var list = await response.json();
    document.getElementById("revenueGrowthChart").innerHTML = `<canvas id="chartangtruongdoanhthunam"></canvas>`
    var chart = document.getElementById("chartangtruongdoanhthunam").getContext('2d');

    const myChart = new Chart(chart, {
        type: 'line',
        data: {
            labels: ["tháng 1", "tháng 2", "tháng 3", "tháng 4",
                "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"
            ],
            datasets: [{
                label: "Tăng trưởng doanh thu năm "+nam,
                backgroundColor: 'rgba(161, 198, 247, 1)',
                borderColor: 'rgb(47, 128, 237)',
                data: list,
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        },
    });
}


async function soDonHuyVaNhan(){
    var url = 'http://localhost:8080/api/thong-ke/public/don-huy-thanh-cong' ;
    const response = await fetch(url, {
    });
    var arr = await response.json();
    document.getElementById("donhuynhan").innerHTML = `<canvas id="charthuyNhan"></canvas>`
    var chart = document.getElementById("charthuyNhan").getContext('2d');
    const myPieChart = new Chart(chart, {
        type: 'pie', // Loại biểu đồ là pie
        data: {
            labels: ['Đơn hủy', 'Đơn nhận'], // Nhãn cho các phần của biểu đồ
            datasets: [{
                label: 'My First Dataset',
                data: arr,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                ],
                borderWidth: 1 // Độ dày viền của biểu đồ
            }]
        },
        options: {
            responsive: true, // Tùy chọn cho phép biểu đồ phản hồi với kích thước màn hình
            plugins: {
                legend: {
                    position: 'top', // Vị trí của chú thích
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return tooltipItem.label + ': ' + tooltipItem.raw; // Hiển thị giá trị khi hover
                        }
                    }
                }
            }
        }
    });
}

function formatmoney(money) {
    const VND = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });
    return VND.format(money);
}