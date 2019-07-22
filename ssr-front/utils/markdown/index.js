var marked = require('marked')
// const DOMPurify = require('dompurify')

/**
 * 转换Markdown 文本 为html ,并对html过滤,防止XSS攻击
 * @param md
 * @returns {*}
 */
export const getMD2HtmlClean = (md) => {
  const dirtyHtml = marked(md)
  // const cleanHtml = DOMPurify.sanitize(dirtyHtml)
  return dirtyHtml
}
