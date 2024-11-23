function search() {
    var startDate = document.getElementById('startDate').value;
    var endDate = document.getElementById('endDate').value;
    var kieu = document.getElementById('searchKieu').value;
    var loai = document.getElementById('searchLoai').value;
    var trangThai = document.getElementById('searchTrangThai').value;
    var magbbsearch = document.getElementById('magbbsearch').value;

    var params = new URLSearchParams();
    if(startDate) params.append('startDate', startDate);
    if(endDate) params.append('endDate', endDate);
    if(kieu) params.append('kieu', kieu);
    if(loai) params.append('loai', loai);
    if(trangThai) params.append('trangThai', trangThai);
    if(magbbsearch) params.append('magg', magbbsearch);


    window.location.href = '/admin/phieu-giam-gia/hien-thi?' + params.toString();
}

function chuyenTrang(page){
    var params = new URLSearchParams();
    // alert(page)
    var uls = new URL(document.URL)
    var startDate = uls.searchParams.get("startDate");
    var endDate = uls.searchParams.get("endDate");
    var kieu = uls.searchParams.get("kieu");
    var loai = uls.searchParams.get("loai");
    var trangThai = uls.searchParams.get("trangThai");
    var magg = uls.searchParams.get("magg");
    params.append('page', page);
    if(startDate != null){
        params.append('startDate', startDate);
    } if(endDate != null){
        params.append('endDate', endDate);
    } if(kieu != null){
        params.append('kieu', kieu);
    }if(loai != null){
        params.append('loai', loai);
    }if(trangThai != null){
        params.append('trangThai', trangThai);
    }if(magg != null){
        params.append('magg', magg);
    }
    window.location.href = '/admin/phieu-giam-gia/hien-thi?' + params.toString();
}

function searchKHCreate(){
    var search = document.getElementById("search").value;
    if(search == ""){
        alert("Hãy nhập dữ liệu");
        return;
    }
    window.location.href = '?search='+search;
}

function chuyenTrangCreatepgg(page){
    var params = new URLSearchParams();
    var uls = new URL(document.URL)
    var search = uls.searchParams.get("search");
    params.append('page', page);
    if(search != null){
        params.append('search', search);
    }
    window.location.href = '/admin/phieu-giam-gia/tao-san-pham?' + params.toString();
}

function searchKHupdate(){
    var search = document.getElementById("search").value;
    if(search == ""){
        alert("Hãy nhập dữ liệu");
        return;
    }
    window.location.href = '?search='+search;
}

function chuyenTrangUpdatepgg(page, id){
    var params = new URLSearchParams();
    var uls = new URL(document.URL)
    var search = uls.searchParams.get("search");
    params.append('page', page);
    if(search != null){
        params.append('search', search);
    }
    window.location.href = '/admin/phieu-giam-gia/update/'+id+'?' + params.toString();
}


document.addEventListener('DOMContentLoaded', function() {
    var rows = document.querySelectorAll('#phieu-giam-gia-tbody tr');

    rows.forEach(function(row) {
        var statusCell = row.querySelector('.status-pgg');
        var label = statusCell.querySelector('.mauSacStatus');
        var status = label ? label.innerText.trim() : '';

        // Áp dụng lớp CSS tương ứng dựa trên trạng thái
        if (status === 'Kết thúc') {
            statusCell.classList.add('status-ket-thuc');
        } else if (status === 'Đang diễn ra') {
            statusCell.classList.add('status-dang-dien-ra');
        } else if (status === 'Sắp diễn ra') {
            statusCell.classList.add('status-sap-dien-ra');
        }
    });
});

function toggleStatuss(checkbox, phieuGiamGiaId) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/phieu-giam-gia/toggle-status/' + phieuGiamGiaId, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            var row = checkbox.closest('tr');
            var statusCell = row.querySelector('.status-pgg');
            var label = statusCell.querySelector('.mauSacStatus');

            // Kiểm tra nếu label tồn tại trước khi thay đổi innerText
            if (label) {
                label.innerText = response.newStatus;
            }

            // Xóa các lớp trạng thái cũ trước khi thêm lớp mới
            statusCell.classList.remove('status-dang-dien-ra', 'status-ket-thuc', 'status-sap-dien-ra');

            if (response.newStatus === 'Kết thúc') {
                statusCell.classList.add('status-ket-thuc');
                checkbox.checked = false;
            } else if (response.newStatus === 'Đang diễn ra') {
                statusCell.classList.add('status-dang-dien-ra');
                checkbox.checked = true;
            } else if (response.newStatus === 'Sắp diễn ra') {
                statusCell.classList.add('status-sap-dien-ra');
                checkbox.checked = true;
            }
        }
    };
    xhr.send();
}



var listProduct = [];
async function chiTietSanPham() {
    var uls = new URL(document.URL)
    var id = uls.searchParams.get("idSP");
    const response = await fetch('http://localhost:8080/api/product/public/get-detail?idSanPham='+id, {
    });
    var obj = await response.json();

    const tableBody = document.querySelector("#productTable tbody");

    // Lặp qua từng màu trong đối tượng data
    var index = 0;
    for (let color in obj) {
        const products = obj[color];
        // Tạo một dòng cho màu mới
        for (let i = 0; i < products.length; i++) {
            ++index;
            listProduct.push(products[i]);
            const row = document.createElement("tr");

            var productCell = document.createElement("td");
            productCell.textContent = index;
            row.appendChild(productCell);

            // Nếu là sản phẩm đầu tiên của màu này, thêm ô màu với rowspan
            if (i === 0) {
                const colorCell = document.createElement("td");
                colorCell.textContent = color;
                colorCell.rowSpan = products.length; // Gộp các dòng lại
                row.appendChild(colorCell);
            }

            // Thêm ô tên sản phẩm
            var productCell = document.createElement("td");
            productCell.textContent = products[i].size.ten;
            row.appendChild(productCell);

            var productCell = document.createElement("td");
            productCell.innerHTML = `<input id="quantity${products[i].id}" value="${products[i].soLuong}" type="number" style="width:60px">`;
            row.appendChild(productCell);

            var productCell = document.createElement("td");
            productCell.innerHTML = `<input id="giaban${products[i].id}" value="${products[i].donGia}" type="number">:VND`;
            row.appendChild(productCell);

            var productCell = document.createElement("td");
            productCell.innerHTML = `<button class="btn btn-danger">xóa</button>`;
            row.appendChild(productCell);

            if (i === 0) {
                const colorCell = document.createElement("td");
                colorCell.innerHTML = `<input type="file" multiple>`;
                colorCell.rowSpan = products.length; // Gộp các dòng lại
                row.appendChild(colorCell);
            }

            // Thêm dòng vào bảng
            tableBody.appendChild(row);
        }
    }
}

async function updateInfor() {
    var listobj = [];
    for(i=0; i<listProduct.length; i++){
        var soluong = document.getElementById("quantity"+listProduct[i].id).value;
        var giatien = document.getElementById("giaban"+listProduct[i].id).value;
        if(soluong <0 || giatien < 0){
            alert("Số lượng hoặc giá tiền phải lớn >= 0");
            return;
        }
        var obj = {
            "idCtsp":listProduct[i].id,
            "donGia":giatien,
            "soLuong":soluong
        }
        listobj.push(obj);
    }
    console.log(listobj);

    const response = await fetch('http://localhost:8080/api/product/public/update-chi-tiet-san-pham', {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(listobj)
    });
    if(response.status < 300){
        alert("Thành công");
        // chuyển hướng trang nếu thành công
        window.location.href = '';
    }
    else{
        alert("thất bại")
    }
}

function ganGiaTri(){
    var soluong =  document.getElementById("globalquantity").value
    var giatien = document.getElementById("globalprice").value;
    if(soluong <0 || giatien < 0){
        alert("Số lượng hoặc giá tiền phải lớn >= 0");
        return;
    }
    for(i=0; i<listProduct.length; i++){
        if(document.getElementById("quantity"+listProduct[i].id).value == 0){
            document.getElementById("quantity"+listProduct[i].id).value = document.getElementById("globalquantity").value;
        }
        if(document.getElementById("giaban"+listProduct[i].id).value == 0){
            document.getElementById("giaban"+listProduct[i].id).value = document.getElementById("globalprice").value;
        }
    }
}


function khoaPgg(idpgg, e){
    var con = confirm("Xác nhận!");
    if(con == false){
        if(e.checked == true){
            e.checked = false
        }
        else{
            e.checked = true;
        }
        return;
    }
    window.location.href = 'doi-trang-thai?id='+idpgg
}