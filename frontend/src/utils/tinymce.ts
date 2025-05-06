import Editor from '@tinymce/tinymce-vue'

export const initTinyMCE = {
  height: 300,
  menubar: false,
  plugins: ['lists', 'link', 'image', 'code', 'fullscreen'],
  toolbar: 'undo redo | bold italic | bullist numlist | link image | code fullscreen',
  content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }',
  branding: false,
  promotion: false,
  license_key: 'gpl',
  base_url: '/tinymce',
  suffix: '.min'
}

export default Editor 