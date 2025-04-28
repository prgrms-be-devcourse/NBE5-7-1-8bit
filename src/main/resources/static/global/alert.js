function showAlert(title, message) {
  Swal.fire({
    title: title,
    text: message,
    confirmButtonText: '확인'
  });
}

function showConfirm(message, onConfirm, onCancel) {
  Swal.fire({
    title: '확인',
    text: message,
    icon: 'question',
    showCancelButton: true,
    confirmButtonColor: '#3085d6', // 확인 버튼 색
    cancelButtonColor: '#d33',     // 취소 버튼 색
    cancelButtonText: '취소',
    confirmButtonText: '확인',
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      if (onConfirm) onConfirm(); // 확인 눌렀을 때 콜백
    } else {
      if (onCancel) onCancel();    // 취소 눌렀을 때 콜백
    }
  });
}
